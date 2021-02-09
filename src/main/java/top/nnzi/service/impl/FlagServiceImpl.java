package top.nnzi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.nnzi.bean.Flag;
import top.nnzi.mapper.FlagMapper;
import top.nnzi.service.FlagService;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-05 20:23
 **/
@Service
public class FlagServiceImpl extends ServiceImpl<FlagMapper, Flag> implements FlagService {
}
