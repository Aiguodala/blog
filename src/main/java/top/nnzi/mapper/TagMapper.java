package top.nnzi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.nnzi.bean.Tag;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 21:10
 **/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    @Select("SELECT * FROM t_blog_tag bt, t_tag t WHERE bt.tag_id = t.id AND blog_id=#{id}")
    List<Tag> findTags(Long id);

    @Select("SELECT *,COUNT(bt.blog_id) blogsNum FROM t_tag t,t_blog_tag bt WHERE t.id = bt.tag_id GROUP BY bt.tag_id ORDER BY COUNT(bt.blog_id) DESC LIMIT #{num}")
    List<Tag> findTop(int num);

}
