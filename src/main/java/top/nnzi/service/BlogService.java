package top.nnzi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.nnzi.bean.Blog;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-05 08:59
 **/
public interface BlogService extends IService<Blog> {
    Page<Blog> adminFindAllBlogs (Page<Blog> page);

    Page<Blog> adminSearchBlogs(Page<Blog> page, Blog blog);

    int saveBlog(Blog blog);

    void deleteBlogAndTagConn(Long id);

    List<Blog> findRecommendBlogsTop6();

    Page<Blog> indexBlogPage(Page<Blog> blogPage);


    void deleteElasticSearch(Long id);

    List<Blog> searchBlogs(String keyTitle) throws IOException;

    Blog blogDetail(Long id);

    Integer getViewCount(Long id);

    Integer getCommentCount(Long id);


    Page<Blog> typesBlogPage(Page<Blog> blogPage, Long id);

    Page<Blog> tagsBlogPage(Page<Blog> blogPage, Long id);
}
