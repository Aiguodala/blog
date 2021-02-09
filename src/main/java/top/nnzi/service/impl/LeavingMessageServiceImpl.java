package top.nnzi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.nnzi.bean.LeavingMessage;
import top.nnzi.mapper.LeavingMessageMapper;
import top.nnzi.service.LeavingMessageService;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 15:23
 **/
@Service
public class LeavingMessageServiceImpl extends ServiceImpl<LeavingMessageMapper, LeavingMessage> implements LeavingMessageService {

    @Autowired
    private LeavingMessageMapper leavingMessageMapper;

    @Override
    public List<LeavingMessage> findTopFive() {
        return leavingMessageMapper.findTopFive();
    }
}
