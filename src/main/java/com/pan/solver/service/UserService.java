package com.pan.solver.service;

import com.pan.solver.entity.User;

public interface UserService {

    User create(User user, String verifyCode);

    User updatePassword(User user, String verifyCode);

    User update(String token, User user);

}