package top.nnzi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.nnzi.bean.LeavingMessage;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 15:23
 **/
@Mapper
public interface LeavingMessageMapper extends BaseMapper<LeavingMessage> {

    @Select("select * from t_leaving_message order by create_time desc limit 5")
    List<LeavingMessage> findTopFive();
}
