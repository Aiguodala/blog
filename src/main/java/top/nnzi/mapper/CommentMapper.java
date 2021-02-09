package top.nnzi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.*;
import top.nnzi.bean.Comment;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 09:46
 **/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select * from t_comment where blog_id=#{blogId} and parent_comment_id is null")
    List<Comment> getByBlogIdParent(Long blogId);


    @Select("select * from t_comment")
    @Results({
            @Result(
                    property = "parentComment",
                    column = "parent_comment_id",
                    javaType = Comment.class,
                    one = @One(select = "top.nnzi.mapper.CommentMapper.selectById")
            ),
            @Result(property = "parentCommentId",column = "parent_comment_id")
    })
    List<Comment> findAllComment();
}
