package top.nnzi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.nnzi.bean.LeavingMessage;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-08 15:23
 **/
public interface LeavingMessageService extends IService<LeavingMessage> {
    List<LeavingMessage> findTopFive();
}
