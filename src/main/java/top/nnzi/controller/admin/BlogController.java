package top.nnzi.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.nnzi.bean.Blog;
import top.nnzi.bean.Type;
import top.nnzi.bean.User;
import top.nnzi.service.BlogService;
import top.nnzi.service.FlagService;
import top.nnzi.service.TagService;
import top.nnzi.service.TypeService;

import javax.servlet.http.HttpSession;
import java.util.*;
/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 15:29
 **/
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private FlagService flagService;

    @GetMapping("/blogs")
    public String blogs (@RequestParam(value = "page",defaultValue = "1") Integer page, Model model,
                         Blog blog) {
        List<Type> types = typeService.list();
        model.addAttribute("types",types);
        Page<Blog> blogPage = new Page<Blog>(page,8);
        Page<Blog> blogPages = blogService.adminFindAllBlogs(blogPage);
        model.addAttribute("blogPages",blogPages);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search (@RequestParam(value = "page",defaultValue = "1") Integer page, Model model,
                         Blog blog) {
        Page<Blog> blogPage = new Page<Blog>(page,8);
        Page<Blog> blogPages = blogService.adminSearchBlogs(blogPage,blog);
        model.addAttribute("blogPages",blogPages);

        return "admin/blogs :: blogList";
    }

    @GetMapping("/saveBlogs")
    public String saveBlogs(Model model) {
        model.addAttribute("types",typeService.list());
        model.addAttribute("flags",flagService.list());
        model.addAttribute("tags",tagService.list());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    @GetMapping("/saveBlogs/{id}")
    public String saveBlogs(@PathVariable Long id, Model model) {
        model.addAttribute("types",typeService.list());
        model.addAttribute("flags",flagService.list());
        model.addAttribute("tags",tagService.list());
        Blog blog = blogService.getById(id);
        blog.setTags(tagService.findTags(id));
        blog.init();
        //System.out.println(blog);
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
        blog.setUser(user);
        blog.setFlagId(blog.getFlagId());
        blog.setTypeId(blog.getTypeId());
        blog.setType(typeService.getById(blog.getTypeId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        int save = blogService.saveBlog(blog);
        if (save == 1) {
            attributes.addFlashAttribute("msg","您已发布了新的文章");
        }else {
            attributes.addFlashAttribute("msg","发布失败！");
        }

        return "redirect:/admin/blogs";
    }

    @GetMapping("/deleteBlog/{id}")
    public String deleteBlog (@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlogAndTagConn(id);
        blogService.removeById(id);

        blogService.deleteElasticSearch(id);
        attributes.addFlashAttribute("msg","删除成功!");
        return "redirect:/admin/blogs";
    }
}
