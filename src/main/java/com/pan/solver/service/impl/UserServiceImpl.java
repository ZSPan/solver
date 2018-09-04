package com.pan.solver.service.impl;

import com.pan.solver.common.util.RedissonClientFactory;
import com.pan.solver.entity.User;
import com.pan.solver.entity.VerifyCode;
import com.pan.solver.entity.VerifyCode.Type;
import com.pan.solver.repository.UserRepository;
import com.pan.solver.service.UserService;
import com.pan.solver.service.VerifyCodeService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    private static final Long VERIFY_CODE_IN_DATE = TimeUnit.MINUTES.toMillis(5);

    private final RedissonClient redissonClient = RedissonClientFactory.getRedissonClient();
    private final UserRepository userRepository;
    private final VerifyCodeService verifyCodeService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, VerifyCodeService verifyCodeService) {
        this.userRepository = userRepository;
        this.verifyCodeService = verifyCodeService;
    }

    @Override
    public User register(User user, String code) {
        String email = user.getEmailAddress();
        VerifyCode verifyCode = verifyCodeService.findLatestVerifyCode(email, Type.REGISTER);
        if (!StringUtils.equals(code, verifyCode.getCode()) ||
                verifyCode.getCreation().getTime() + VERIFY_CODE_IN_DATE >=
                        System.currentTimeMillis()) {
            throw new RuntimeException("code is invalid");
        }
        user.setPassword(new String(DigestUtils.md5Digest(user.getPassword().getBytes())));
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loginByEmailAddress(String emailAddress, String password) {
        //verify emailAddress password not null
        //verify user not null
        User user = userRepository.findFirstByEmailAddressEquals(emailAddress);
        if (new String(DigestUtils.md5Digest(password.getBytes())).equals(user.getPassword())) {
            String token = Jwts.builder()
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, "secret")
                    .compact();
            redissonClient.getMap("tokenMap").put(user.getEmailAddress(), token);
            return token;
        } else {
            return "{\"errorCode\":\"User's email address does not match the password!\"}";
        }

    }

    @Override
    public String loginByNickname(String nickname, String password) {
        User user = userRepository.findFirstByNicknameEquals(nickname);
        if (new String(DigestUtils.md5Digest(password.getBytes())).equals(user.getPassword())) {
            String token = Jwts.builder()
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, "secret")
                    .compact();
            redissonClient.getMap("tokenMap").put(user.getEmailAddress(), token);
            return token;
        } else {
            return "{\"errorCode\":\"User's nickname does not match the password!\"}";
        }
    }
}