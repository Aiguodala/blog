package top.nnzi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-03 21:50
 **/
@Data
@ToString
@AllArgsConstructor
public class RequestLog {
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;
}
