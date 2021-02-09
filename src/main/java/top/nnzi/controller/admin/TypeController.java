package top.nnzi.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.nnzi.bean.Type;
import top.nnzi.service.TypeService;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 16:32
 **/

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types (Model model) {
        List<Type> types = typeService.list();
        model.addAttribute("types",types);
        return "admin/types";
    }

    @PostMapping("/saveType")
    public String saveType (Type type, RedirectAttributes attributes) {
        boolean save = typeService.save(type);
        if (save) {
            String name = type.getName();
            attributes.addFlashAttribute("msg","操作成功，您刚刚新加入了 "+name+" 类别");
        }else {
            attributes.addFlashAttribute("msg","操作失败！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/beforeUpdateType/{id}")
    public String beforeUpdate (@PathVariable Long id, Model model) {
        Type type = typeService.getById(id);
        model.addAttribute("type",type);
        return "admin/types-input";
    }

    @PostMapping("/updateType/{id}")
    public String updateType (@PathVariable Long id, Type type,RedirectAttributes attributes) {
        Type oraiginType = typeService.getById(id);
        String oraiginName = oraiginType.getName();
        boolean update = typeService.updateById(type);

        if (update) {
            String name = type.getName();
            if (name.equals(oraiginName)) {
                attributes.addFlashAttribute("msg","操作失败! 您修改的名字与之前相同");
            }else {
                attributes.addFlashAttribute("msg","操作成功，您刚刚修改了 "+name+" 类别");
            }

        }else {
            attributes.addFlashAttribute("msg","操作失败！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/deleteType/{id}")
    public String deleteType (@PathVariable Long id,RedirectAttributes attributes) {

        Type type = typeService.getById(id);
        String name = type.getName();

        boolean remove = typeService.removeById(id);
        if (remove) {
            attributes.addFlashAttribute("msg","删除成功,您刚刚删除了 "+name+" 类别");
        }else {
            attributes.addFlashAttribute("msg","该类别下有博客，无法删除");
        }

        return "redirect:/admin/types";
    }


}
