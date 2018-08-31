package com.pan.solver.util;

import java.util.Random;

/**
 * @author yemingfeng
 */

public class VerifyCodeUtil {

    private static final Random RANDOM = new Random();

    public static String generateVerifyCode() {
        return String.valueOf(RANDOM.nextInt(89999) + 10000);
    }
}
