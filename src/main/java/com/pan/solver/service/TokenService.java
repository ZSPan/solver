package com.pan.solver.service;

import com.pan.solver.entity.User;

/**
 * @Author Shipan.Zeng
 */
public interface TokenService {

    String create(User user) throws Exception;
}
