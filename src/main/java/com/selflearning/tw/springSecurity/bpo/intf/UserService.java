package com.selflearning.tw.springSecurity.bpo.intf;

import com.selflearning.tw.springSecurity.model.User;
import com.selflearning.tw.springSecurity.vo.UserVO;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    UserVO getUserByUsername(String username);

    void createUser(User user);

    boolean updateUser(User user);
}
