package com.pan.solver.controller;

import com.pan.solver.entity.User;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping(value = "update")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping(value = "login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }
}