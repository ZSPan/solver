package com.pan.solver.service;

import com.pan.solver.entity.User;

public interface UserService {

    User register(User user, String verifyCode);

    String loginByEmailAddress(String emailAddress, String passowrd);

    String loginByNickname(String nickName, String password);

}