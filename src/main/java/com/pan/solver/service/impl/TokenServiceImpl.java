package com.pan.solver.service.impl;

import com.pan.solver.util.MD5Util;
import com.pan.solver.entity.User;
import com.pan.solver.repository.UserRepository;
import com.pan.solver.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private UserRepository userRepository;

    @Autowired
    public TokenServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String create(User user) {
        User userInDB;
        if (StringUtils.isEmpty(user.getEmailAddress())) {
            userInDB = userRepository.findByEmailAddress(user.getEmailAddress());
        } else if (StringUtils.isEmpty(user.getNickname())) {
            userInDB = userRepository.findByNickname(user.getNickname());
        } else {
            throw new RuntimeException("Invalid argument");
        }

        if (userInDB == null) {
            throw new RuntimeException("Not existed");
        } else if (StringUtils.equals(MD5Util.digest(user.getPassword()), userInDB.getPassword())) {
            return userInDB.getId() + "_" + System.currentTimeMillis();
        } else {
            throw new RuntimeException("Incorrect password!");
        }
    }
}
