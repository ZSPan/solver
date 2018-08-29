package com.pan.solver.service.impl;

import com.pan.solver.entity.User;
import com.pan.solver.repository.UserRepository;
import com.pan.solver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User insertOrUpdate(User user) {
        //TODO: save or update
        return userRepository.save(user);
    }
}
