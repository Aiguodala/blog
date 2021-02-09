package top.nnzi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.nnzi.bean.Tag;
import top.nnzi.mapper.TagMapper;
import top.nnzi.service.TagService;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 21:11
 **/
@Service
public class TagServcieImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> listTag(String ids) {
        return tagMapper.selectBatchIds(convertToList(ids));
    }

    @Override
    public List<Tag> findTags(Long id) {

        return tagMapper.findTags(id);
    }

    @Override
    public List<Tag> findTop(int num) {
        return tagMapper.findTop(num);
    }



    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
