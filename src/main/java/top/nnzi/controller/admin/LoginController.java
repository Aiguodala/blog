package top.nnzi.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.nnzi.bean.User;
import top.nnzi.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 14:17
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage () {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login (@RequestParam("username") String username, @RequestParam("password") String password,
                         HttpSession session,
                         RedirectAttributes attributes) {

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser != null) {
            return "admin/index";
        }else {
            User user = userService.findByUsernameAndPassword (username, password);
            if (user != null) {
                user.setPassword(null);
                session.setAttribute("user",user);
                return "admin/index";
            }
        }
        attributes.addFlashAttribute("msg","用户名或密码错误");
        return "redirect:/admin";
    }

    @GetMapping("/loginOut")
    public String loginOut (HttpSession session) {

        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
