package com.pan.solver;

import com.pan.solver.entity.VerifyCode.Type;
import com.pan.solver.service.VerifyCodeService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yemingfeng
 */
@Ignore
@SpringBootTest
@RunWith(SpringRunner.class)
public class VerifyCodeServiceTest {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Test
    public void sendMailAsync() {
        verifyCodeService.sendVerifyCode("393162333@qq.com", Type.REGISTER);
    }
}
