package top.nnzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.nnzi.bean.Blog;
import top.nnzi.bean.Tag;
import top.nnzi.service.BlogService;
import top.nnzi.service.TagService;
import top.nnzi.service.TypeService;


import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 16:03
 **/
@Controller
public class TagShowController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String types(@PathVariable("id") Long id, Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page) {

        List<Tag> tags = tagService.findTop(10000000);
        Page<Blog> blogPage = new Page<>(page, 8);
        Page<Blog> blogPages = blogService.tagsBlogPage(blogPage,id);
        model.addAttribute("blogPages",blogPages);
        model.addAttribute("types",typeService.list());
        model.addAttribute("tags",tags);
        model.addAttribute("activeTagId",id);
        return "tags";

    }
}
