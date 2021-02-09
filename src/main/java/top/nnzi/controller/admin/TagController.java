package top.nnzi.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.nnzi.bean.Tag;
import top.nnzi.bean.Type;
import top.nnzi.service.TagService;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 21:13
 **/
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags (@RequestParam(value = "page",defaultValue = "1") Integer page,  Model model) {
        Page<Tag> tagPage = new Page<Tag>(page,5);

        Page<Tag> tagPages = tagService.page(tagPage, null);

        model.addAttribute("tagPages",tagPages);
        return "admin/tags";
    }

    @PostMapping("/saveTag")
    public String saveTag (@RequestParam(name = "page") Integer page,Tag tag, RedirectAttributes attributes) {
        boolean save = tagService.save(tag);
        if (save) {
            String name = tag.getName();
            attributes.addFlashAttribute("msg","操作成功,您刚刚新增了 "+name+" 标签");
        }else {
            attributes.addFlashAttribute("msg","操作失败！");
        }
        return "redirect:/admin/tags?page="+page;
    }

    @GetMapping("/beforeUpdateTag/{id}")
    public String beforeUpdateTag (@PathVariable("id") Long id, @RequestParam(name = "page") Integer page,Model model) {
        Tag tag = tagService.getById(id);
        model.addAttribute("tag",tag);
        model.addAttribute("page",page);
        return "admin/tags-input";
    }

    @PostMapping("/updateTag/{id}")
    public String updateType (@PathVariable("id") Long id, @RequestParam(name = "page") Integer page, Tag tag, RedirectAttributes attributes) {
        Tag tag1 = tagService.getById(id);
        String originName = tag1.getName();
        boolean update = tagService.updateById(tag);

        if (update) {
            String name = tag.getName();
            if (name.equals(originName)) {
                attributes.addFlashAttribute("msg","操作失败! 您修改的名字与之前相同");
            }else {
                attributes.addFlashAttribute("msg","操作成功，您刚刚修改了 "+name+" 标签");
            }

        }else {
            attributes.addFlashAttribute("msg","操作失败！");
        }
        return "redirect:/admin/tags?page="+page;
    }

    @GetMapping("/deleteTag/{id}")
    public String deleteTag (@PathVariable("id") Long id, @RequestParam(name = "page") Integer page, RedirectAttributes attributes) {
        Tag tag1 = tagService.getById(id);
        String originName = tag1.getName();
        boolean remove = tagService.removeById(id);
        if (remove) {
            attributes.addFlashAttribute("msg","删除成功,您刚刚删除了 "+originName+" 标签");
        }else {
            attributes.addFlashAttribute("msg","删除失败！");
        }
        return "redirect:/admin/tags?page="+page;
    }


}
