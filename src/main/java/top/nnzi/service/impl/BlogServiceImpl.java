package top.nnzi.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.nnzi.bean.Blog;
import top.nnzi.bean.Tag;
import top.nnzi.mapper.BlogMapper;
import top.nnzi.mapper.CommentMapper;
import top.nnzi.service.BlogService;
import top.nnzi.service.TagService;
import top.nnzi.utils.MarkDownUtil;

import javax.naming.directory.SearchResult;
import javax.swing.text.Highlighter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-05 09:00
 **/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Page<Blog> adminFindAllBlogs(Page<Blog> page) {
        return blogMapper.adminFindAllBlogs(page);
    }

    @Override
    public Page<Blog> adminSearchBlogs(Page<Blog> page, Blog blog) {
        return blogMapper.adminSearchBlogs(page, blog);
    }

    @Override
    public int saveBlog(Blog blog) {
        int res = 0;
        if (blog.getId() == null) {
            blog.setUpdateTime(new Date());
            blog.setCreateTime(new Date());
            blog.setViews(0);
            blog.setCommentCount(0);
            res = blogMapper.insert(blog);
            List<Tag> tags = blog.getTags();
            for (Tag tag : tags) {
                blogMapper.saveBlogAndTag(blog.getId(), tag.getId());
            }
            try {
                IndexRequest request = new IndexRequest("blog");
                request.id(String.valueOf(blog.getId()));
                //将数据放入请求
                request.source(JSON.toJSONString(blog), XContentType.JSON);
                //客户端发送请求，获得响应结果
                IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            blog.setUpdateTime(new Date());
            blogMapper.updateById(blog);
            List<Tag> tags = blog.getTags();
            blogMapper.deleteOriginBlogAndTag(blog.getId());
            for (Tag tag : tags) {
                blogMapper.saveBlogAndTag(blog.getId(), tag.getId());
            }
            res = 1;
        }

        return res;
    }

    @Override
    public void deleteBlogAndTagConn(Long id) {
        blogMapper.deleteOriginBlogAndTag(id);
    }

    @Override
    public List<Blog> findRecommendBlogsTop6() {
        return blogMapper.findRecommendBlogsTop6();
    }

    @Override
    public Page<Blog> indexBlogPage(Page<Blog> blogPage) {
        return blogMapper.indexBlogPage(blogPage);
    }



    public void deleteElasticSearch(Long id) {
        DeleteRequest request = new DeleteRequest("blog", String.valueOf(id));
        request.timeout("1s");
        try {
            restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Blog> searchBlogs(String keyTitle) throws IOException {

        SearchRequest searchRequest = new SearchRequest("blog");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(0);

        //精准匹配
        QueryBuilder termQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title",keyTitle))
                .should(QueryBuilders.matchQuery("description",keyTitle));
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.field("description");
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //System.out.println(searchResponse);

        ArrayList<Blog> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            HighlightField description = highlightFields.get("description");
            Map<String,Object> sourceAsMap = hit.getSourceAsMap();
            if (title != null) {
                Text[] fragments = title.fragments();
                String newTitle = "";
                for (Text text : fragments) {
                    newTitle += text;
                }
                sourceAsMap.put("title",newTitle);
            }
            if (description != null) {
                Text[] fragments = description.fragments();
                String newDesc = "";
                for (Text text : fragments) {
                    newDesc += text;
                }
                sourceAsMap.put("description",newDesc);
            }


            list.add(JSON.parseObject(JSON.toJSONString(sourceAsMap),Blog.class));
        }

        return list;
    }

    @Override
    public Blog blogDetail(Long id) {
        Blog blog = blogMapper.blogDetail(id);
        List<Tag> tags =  tagService.findTags(id);
        Integer size = Math.toIntExact(redisTemplate.boundSetOps(String.valueOf(id)).size());
        blog.setViews(size+blog.getViews());
        blog.setTags(tags);
        blog.setContent(MarkDownUtil.markdownToHtmlExtensions(blog.getContent()));
        blog.setComments(commentMapper.getByBlogIdParent(id));
        return blog;
    }

    @Override
    public Integer getViewCount(Long id) {
        return blogMapper.getViewCount(id);
    }

    @Override
    public Integer getCommentCount(Long id) {
        return blogMapper.getCommentCount(id);
    }

    @Override
    public Page<Blog> typesBlogPage(Page<Blog> blogPage, Long id) {
        return blogMapper.typesBlogPage(blogPage,id);
    }

    @Override
    public Page<Blog> tagsBlogPage(Page<Blog> blogPage, Long id) {
        return blogMapper.tagsBlogPage(blogPage,id);
    }

}
