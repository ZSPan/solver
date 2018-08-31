package com.pan.solver.controller;

import com.pan.solver.dto.UserDto;
import com.pan.solver.mapper.UserMapper;
import com.pan.solver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "register")
    public UserDto register(@RequestBody UserDto user) {
        return userMapper.toDto(userService.register(userMapper.toEntity(user)));
    }

    @PostMapping(value = "update")
    public UserDto update(@RequestBody UserDto user) {
        return userMapper.toDto(userService.update(userMapper.toEntity(user)));
    }

    @PostMapping(value = "login")
    public UserDto login(@RequestBody UserDto user) {
        return userMapper.toDto(userService.login(userMapper.toEntity(user)));
    }
}