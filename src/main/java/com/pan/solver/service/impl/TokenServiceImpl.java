package com.pan.solver.service.impl;

import com.pan.solver.entity.User;
import com.pan.solver.redis.RedisService;
import com.pan.solver.repository.UserRepository;
import com.pan.solver.service.TokenService;
import com.pan.solver.util.Base64Util;
import com.pan.solver.util.MD5Util;
import com.pan.solver.util.RedisKeyGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private UserRepository userRepository;
    private RedisService redisService;

    @Autowired
    public TokenServiceImpl(UserRepository userRepository, RedisService redisService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    @Override
    public String create(User user) {
        User userInDB;
        if (!StringUtils.isEmpty(user.getEmailAddress())) {
            userInDB = userRepository.findByEmailAddress(user.getEmailAddress());
        } else if (!StringUtils.isEmpty(user.getNickname())) {
            userInDB = userRepository.findByNickname(user.getNickname());
        } else {
            throw new RuntimeException("Invalid argument");
        }

        if (userInDB == null) {
            throw new RuntimeException("Not existed");
        } else if (StringUtils.equals(MD5Util.digest(user.getPassword()), userInDB.getPassword())) {
            String token = Base64Util.encode(userInDB.getEmailAddress() + "_" + System.currentTimeMillis());
            String tokenKey = RedisKeyGenerator.genToken(userInDB.getEmailAddress());
            redisService.set(tokenKey, token);
            redisService.expire(tokenKey, 24 * 60 * 60);
            return Base64Util.encode(token);
        } else {
            throw new RuntimeException("Incorrect password!");
        }
    }

    @Override
    public boolean ifValid(String token) {
        String decodeToken = Base64Util.decode(token);
        String[] tokenMessage = decodeToken.split("_");
        long createTime = Long.valueOf(tokenMessage[1]);
        if (System.currentTimeMillis() - createTime > 24 * 60 * 60) {
            return false;
        } else {
            String tokenKey = RedisKeyGenerator.genToken(tokenMessage[0]);
            return StringUtils.equals(token, redisService.get(tokenKey));
        }
    }


}
