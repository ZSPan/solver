package com.pan.solver.controller;

import com.pan.solver.dto.UserDto;
import com.pan.solver.mapper.UserMapper;
import com.pan.solver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public UserDto create(@RequestBody UserDto user) {
        return userMapper.toDto(userService.create(userMapper.toEntity(user), user.getVerifyCode()));
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto user) {
        return userMapper.toDto(userService.update(userMapper.toEntity(user)));

    }

    @PutMapping("/password")
    public UserDto updatePassword(@RequestBody UserDto user) {
        return userMapper.toDto(userService.updatePassword(userMapper.toEntity(user), user.getVerifyCode()));


    }
}