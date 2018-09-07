package com.pan.solver.controller;

import com.pan.solver.dto.UserDto;
import com.pan.solver.mapper.UserMapper;
import com.pan.solver.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;
    private final UserMapper userMapper;

    @Autowired
    public TokenController(TokenService tokenService, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public String create(UserDto userDto) throws Exception {
        return tokenService.create(userMapper.toEntity(userDto));
    }


}
