package top.nnzi.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-05 20:15
 **/
@Data
@ToString
@TableName("t_flag")
public class Flag {

    private Long id;
    private String name;
}
