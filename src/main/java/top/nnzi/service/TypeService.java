package top.nnzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.nnzi.bean.Type;
import java.util.*;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 16:37
 **/
public interface TypeService extends IService<Type> {
    List<Type> findTop(int num);
}
