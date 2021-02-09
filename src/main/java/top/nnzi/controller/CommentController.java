package top.nnzi.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.nnzi.bean.Comment;
import java.util.List;

import top.nnzi.bean.User;
import top.nnzi.service.BlogService;
import top.nnzi.service.CommentService;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 09:24
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> comments = commentService.getByBlogIdParent(blogId);
        model.addAttribute("comments",comments);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post (Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }
        Long blogId = comment.getBlogId();
        comment.setBlog(blogService.getById(blogId));
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }


}
