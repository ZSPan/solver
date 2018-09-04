package com.pan.solver.service;

import com.pan.solver.entity.VerifyCode;
import com.pan.solver.entity.VerifyCode.Type;

/**
 * @author yemingfeng
 */

public interface VerifyCodeService {

    void sendVerifyCode(String emailAddress, Type type);

    VerifyCode findLatestVerifyCode(String emailAddress, Type type);

}