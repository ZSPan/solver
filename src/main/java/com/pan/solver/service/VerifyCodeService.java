package com.pan.solver.service;

import com.pan.solver.entity.VerifyCode;
import com.pan.solver.entity.VerifyCode.Type;

/**
 * @author yemingfeng
 */

public interface VerifyCodeService {

    void sendVerifyCode(String email, Type type);

    VerifyCode findLatestVerifyCode(String email, Type type);

}