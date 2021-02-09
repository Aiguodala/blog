package top.nnzi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.nnzi.bean.Picture;
import top.nnzi.mapper.PictureMapper;
import top.nnzi.service.PictureService;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 22:01
 **/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {
}
