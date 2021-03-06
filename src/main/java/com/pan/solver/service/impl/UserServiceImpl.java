package com.pan.solver.service.impl;

import com.pan.solver.entity.User;
import com.pan.solver.entity.VerifyCode;
import com.pan.solver.entity.VerifyCode.Type;
import com.pan.solver.repository.UserRepository;
import com.pan.solver.service.TokenService;
import com.pan.solver.service.UserService;
import com.pan.solver.service.VerifyCodeService;
import com.pan.solver.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private static final Long VERIFY_CODE_IN_DATE = TimeUnit.MINUTES.toMillis(5);

    private final UserRepository userRepository;
    private final VerifyCodeService verifyCodeService;
    private final TokenService tokenService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, VerifyCodeService verifyCodeService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.verifyCodeService = verifyCodeService;
        this.tokenService = tokenService;
    }

    @Override
    public User create(User user, String code) {
        String email = user.getEmailAddress();
        VerifyCode verifyCode = verifyCodeService.findLatestVerifyCode(email, Type.REGISTER);
        if (!StringUtils.equals(code, verifyCode.getCode()) ||
                verifyCode.getCreation().getTime() + VERIFY_CODE_IN_DATE >=
                        System.currentTimeMillis()) {
            throw new RuntimeException("code is invalid");
        }
        user.setPassword(MD5Util.digest(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User updatePassword(User user, String code) {
        String email = user.getEmailAddress();
        VerifyCode verifyCode = verifyCodeService.findLatestVerifyCode(email, Type.CHANGE_PASSWORD);
        if (!StringUtils.equals(code, verifyCode.getCode()) ||
                verifyCode.getCreation().getTime() + VERIFY_CODE_IN_DATE >=
                        System.currentTimeMillis()) {
            throw new RuntimeException("code in invalid");
        }
        user.setPassword(MD5Util.digest(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(String token, User user) {
        if (!tokenService.ifValid(token)) {
            throw new RuntimeException("Token is invalid");
        }
        return userRepository.save(user);
    }
}