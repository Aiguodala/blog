package top.nnzi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.nnzi.bean.User;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 14:10
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(String username, String password);
}
