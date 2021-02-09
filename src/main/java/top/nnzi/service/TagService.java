package top.nnzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.nnzi.bean.Tag;
import java.util.*;
/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 21:11
 **/
public interface TagService extends IService<Tag> {
    List<Tag> listTag (String ids);

    List<Tag> findTags(Long id);

    List<Tag> findTop(int num);


}
