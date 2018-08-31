package com.pan.solver.service;

import com.pan.solver.entity.User;

public interface UserService {

    User register(User user);

    User update(User user);

    User login(User user);


}