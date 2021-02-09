package top.nnzi.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 21:59
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_picture")
public class Picture {
    private Long id;
    private String avatar;
}
