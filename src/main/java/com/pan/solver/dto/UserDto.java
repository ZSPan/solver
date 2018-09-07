package com.pan.solver.dto;

import java.util.Date;
import lombok.Data;

/**
 * @author yemingfeng
 */

@Data
public class UserDto {

    private Long id;
    private String emailAddress;
    private String nickname;
    private String password;
    private String sex;
    private String headPortrait;
    private String district;
    private String motto;
    private Long lastLoginTime;
    private Date creation;
    private Date modification;

    private String verifyCode;
}