package top.nnzi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.nnzi.bean.Blog;
import top.nnzi.bean.Type;
import top.nnzi.service.BlogService;
import top.nnzi.service.TypeService;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 16:03
 **/
@Controller
public class TypeShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable("id") Long id, Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page) {

        List<Type> types = typeService.findTop(100000);
        Page<Blog> blogPage = new Page<>(page,8);
        Page<Blog> blogPages = blogService.typesBlogPage(blogPage,id);
        model.addAttribute("blogPages",blogPages);
        model.addAttribute("types",types);
        model.addAttribute("activeTypeId",id);
        return "types";

    }
}
