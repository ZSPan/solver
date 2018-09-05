package com.pan.solver.service;

import com.pan.solver.dto.UserDto;

/**
 * @Author Shipan.Zeng
 */
public interface TokenService {

    String create(UserDto userDto) throws Exception;
}
