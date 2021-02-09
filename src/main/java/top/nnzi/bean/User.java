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
 * @create: 2021-02-04 12:54
 **/

@Data
@ToString
@NoArgsConstructor
@TableName("t_user")
public class User {

    @TableId
    private Long id;
    private String username;
    private String password;
    private String email;
    private String avatar  = "https://unsplash.it/100/100?image=1005";
    private Integer type;
    private Date createTime;

    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();


}
