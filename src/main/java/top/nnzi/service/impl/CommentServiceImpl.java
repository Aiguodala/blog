package top.nnzi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.nnzi.bean.Blog;
import top.nnzi.bean.Comment;
import top.nnzi.mapper.BlogMapper;
import top.nnzi.mapper.CommentMapper;
import top.nnzi.service.CommentService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 09:46
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogMapper blogMapper;
    @Override
    public List<Comment> getByBlogIdParent(Long blogId) {
        List<Comment> comments = commentMapper.getByBlogIdParent(blogId);
        for (Comment comment : comments) {
            comment.setReplyComments(getSonCommentsById(comment.getId()));
        }
        return comments;
    }
    public List<Comment> getSonCommentsById (Long id) {
        List<Comment> resultList = new LinkedList<>();
        List<Comment> commentList = commentMapper.findAllComment();
        for (Comment comment : commentList) {
            Comment tmp = comment;
            if (tmp.getParentComment() != null && tmp.getParentComment().getParentCommentId() != null && tmp.getParentComment().getParentComment() == null) {
                Comment selectById = commentMapper.selectById(tmp.getParentComment().getParentCommentId());
                tmp.getParentComment().setParentComment(selectById);
            }
            if (tmp.getParentComment() != null) {
                while (tmp.getParentComment().getParentComment() != null) {
                    tmp = tmp.getParentComment();
                    if (tmp.getParentComment() != null && tmp.getParentComment().getParentCommentId() != null && tmp.getParentComment().getParentComment() == null) {
                        Comment selectById = commentMapper.selectById(tmp.getParentComment().getParentCommentId());
                        tmp.getParentComment().setParentComment(selectById);
                    }
                }
                Long parentId = tmp.getParentComment().getId();
                if (parentId.equals(id))
                    resultList.add(comment);
            }
        }
        return resultList;
    }

    @Override
    public void saveComment(Comment comment) {
        if (comment.getParentCommentId() == -1) {
            comment.setParentCommentId(null);
        }
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
        Long blogId = comment.getBlogId();
        blogMapper.addCommentCount(blogId);
    }


}
