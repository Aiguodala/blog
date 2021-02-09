package top.nnzi.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.nnzi.bean.Picture;
import top.nnzi.service.PictureService;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 21:58
 **/
@Controller
@RequestMapping("/admin")
public class PictureController {


    @Autowired
    private PictureService pictureService;

    @GetMapping("/pictures")
    public String pictures(Model model) {
        model.addAttribute("pictures",pictureService.list());
        return "admin/pictures";
    }

    @GetMapping("/deletePicture/{id}")
    public String deletePicture(@PathVariable Long id, RedirectAttributes attributes) {
        boolean b = pictureService.removeById(id);
        if (b) {
            attributes.addFlashAttribute("msg","删除成功！");
        }else {
            attributes.addFlashAttribute("msg","删除失败！请检查原因");
        }
        return "redirect:/admin/pictures";
    }

    @PostMapping("/savePicture")
    public String savePicture(Picture picture, RedirectAttributes attributes) {
        boolean b = pictureService.save(picture);
        if (b) {
            attributes.addFlashAttribute("msg","新增成功！");
        }else {
            attributes.addFlashAttribute("msg","新增失败！请检查原因");
        }

        return "redirect:/admin/pictures";
    }
}
