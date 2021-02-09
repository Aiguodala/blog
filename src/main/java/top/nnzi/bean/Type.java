package top.nnzi.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 12:43
 **/
@Data
@ToString
@NoArgsConstructor
@TableName("t_type")
public class Type {

    @TableId
    private Long id;
    private String name;

    @TableField(exist = false)
    List<Blog> blogs = new ArrayList<>();
    @TableField(exist = false)
    private Integer blogsNum;
}
