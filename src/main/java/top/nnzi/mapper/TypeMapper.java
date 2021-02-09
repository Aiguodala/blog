package top.nnzi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.nnzi.bean.Type;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 16:39
 **/
@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    @Select("SELECT t.id,t.name,COUNT(b.id) blogsNum FROM t_blog b,t_type t WHERE t.id = b.type_id GROUP BY type_id ORDER BY COUNT(b.id) DESC LIMIT #{num}")
    List<Type> findTop(int num);

}
