package com.pan.solver.controller.dps;

import com.pan.solver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    //@Autowired
    //public UserService userService;

    /**
     * @param username
     * @param password
     * @Description：用户注册
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser(@RequestParam String username, @RequestParam String password) {

    }

    /**
     * @param username
     * @param password
     * @Description:用户登录
     */
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public void loginUser(@RequestParam String username, @RequestParam String password) {

    }

    /**
     * @param data
     * @Description:更新用户
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public void updateUser(@RequestParam String data) {

    }


}
