package top.nnzi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.nnzi.bean.Type;
import top.nnzi.mapper.TypeMapper;
import top.nnzi.service.TypeService;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 16:39
 **/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private TypeMapper typeMapper;
    @Override
    public List<Type> findTop(int num) {
        return typeMapper.findTop(num);
    }
}
