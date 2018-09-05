package com.pan.solver.service.impl;

import com.pan.solver.common.util.DigestUtil;
import com.pan.solver.dto.UserDto;
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
    public String create(UserDto userDto) throws Exception {
        User user;
        if (!"".equals(userDto.getEmail()) && userDto.getEmail() != null) {
            user = userRepository.findFirstByEmailAddressEquals(userDto.getEmail());
        } else if (!"".equals(userDto.getNickname()) && userDto.getNickname() != null) {
            user = userRepository.findFirstByNicknameEquals(userDto.getNickname());
        } else {
            throw new Exception("Invalid argument");
        }

        if (StringUtils.equals(DigestUtil.digest(userDto.getPassword()), user.getPassword())) {
            return user.getId() + "_" + System.currentTimeMillis();
        } else {
            throw new Exception("Incorrect password!");
        }
    }
}
