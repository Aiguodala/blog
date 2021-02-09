package top.nnzi.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 12:47
 **/

@Data
@ToString
@NoArgsConstructor
@TableName("t_comment")
public class Comment {

    @TableId
    private Long id;
    private String nickname;
    private String email;
    private String content;//评论内容
    private String avatar;//头像
    private Date createTime;
    private Long blogId;
    private Long parentCommentId;
    private Boolean adminComment;

    @TableField(exist = false)
    private Blog blog;

    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>();//一个父类评论有很多子评论

    @TableField(exist = false)
    private Comment parentComment;//一个子评论只有一个父评论

}
