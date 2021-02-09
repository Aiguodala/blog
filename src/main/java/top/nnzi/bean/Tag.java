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
 * @create: 2021-02-04 12:44
 **/
@Data
@ToString
@NoArgsConstructor
@TableName("t_tag")
public class Tag {

    @TableId
    private Long id;
    private String name;

    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();
    @TableField(exist = false)
    private Integer blogsNum;
}
