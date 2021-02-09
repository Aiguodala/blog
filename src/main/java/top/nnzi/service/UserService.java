package top.nnzi.service;

import top.nnzi.bean.User;

/**
 * @program: blog
 * @description:
 * @author: A.iguodala
 * @create: 2021-02-04 14:08
 **/
public interface UserService {
    User findByUsernameAndPassword(String username, String password);
}
