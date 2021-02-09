package top.nnzi.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 15:19
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_leaving_message")
public class LeavingMessage {

    @TableId
    private Long id;
    private String content;
    private String nickname;
    private Date createTime;

}
