package top.nnzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.nnzi.bean.*;
import top.nnzi.service.*;

import java.io.IOException;
import java.util.*;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-03 20:31
 **/
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private LeavingMessageService leavingMessageService;

    @GetMapping("/")
    public String index(@RequestParam(value = "page",defaultValue = "1")Integer page, Model model) {

        Page<Blog> blogPage = new Page<>(page,8);
        Page<Blog> blogPages = blogService.indexBlogPage(blogPage);
        model.addAttribute("blogPages",blogPages);

        List<Type> types = typeService.findTop(6);
        model.addAttribute("types",types);

        List<Tag> tags = tagService.findTop(10);
        model.addAttribute("tags",tags);

        List<LeavingMessage> leavingMessages = leavingMessageService.findTopFive();
        model.addAttribute("leavingMessages",leavingMessages);

        List<Blog> recommendBlogs = blogService.findRecommendBlogsTop6();
        model.addAttribute("recommendBlogs",recommendBlogs);

        return "index";
    }
    @PostMapping("/search")
    public String search(@RequestParam(value = "page",defaultValue = "1")Integer page, Model model,
                         @RequestParam("query") String query) {
        List<Blog> blogList = null;
        try {
            blogList = blogService.searchBlogs(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Blog blog : blogList) {
            blog.setCommentCount(blogService.getCommentCount(blog.getId()));
            blog.setViews(blogService.getViewCount(blog.getId()));
        }

        //当前页
        int current = page;
        //每页数据条数
        int size = 8;
        Page<Blog> blogs = new Page<>(current,size);
        int count = blogList.size();
        List<Blog> pageList = new ArrayList<>();
        //计算当前页第一条数据的下标
        int currId = current>1 ? (current-1)*size:0;
        for (int i=0; i<size && i<count - currId;i++){
            pageList.add(blogList.get(currId+i));
        }
        blogs.setSize(size);
        blogs.setCurrent(current);
        blogs.setTotal(count);
        //计算分页总页数
        blogs.setPages(count %10 == 0 ? count/10 :count/10+1);
        blogs.setRecords(pageList);

        model.addAttribute("types",typeService.list());
        model.addAttribute("blogPages",blogs);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        Blog blog = blogService.blogDetail(id);
        model.addAttribute("blog",blog);
        return "blog";
    }


    @PostMapping("/leavingMessage")
    public String leavingMessage(LeavingMessage leavingMessage) {
        leavingMessage.setCreateTime(new Date());
        leavingMessageService.save(leavingMessage);
        return "redirect:/";
    }

    @GetMapping("/pictures")
    public String pictures(Model model) {
        model.addAttribute("types",typeService.list());
        model.addAttribute("pictures",pictureService.list());
        return "pictures";
    }
}
