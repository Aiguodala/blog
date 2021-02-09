package top.nnzi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.nnzi.bean.User;
import top.nnzi.mapper.UserMapper;
import top.nnzi.service.UserService;
import top.nnzi.utils.MD5Util;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 14:10
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, MD5Util.code(password));
    }
}
