package top.nnzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.nnzi.bean.Comment;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 09:45
 **/
public interface CommentService extends IService<Comment> {
    List<Comment> getByBlogIdParent(Long blogId);

    void saveComment(Comment comment);


}
