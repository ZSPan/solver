package com.pan.solver.util;

import java.util.Base64;

/**
 * @author yemingfeng
 */

public class Base64Util {

    public static String encode(String originString) {
        return new String(Base64.getEncoder().encode(originString.getBytes()));
    }

    public static String decode(String originString) {
        return new String(Base64.getDecoder().decode(originString.getBytes()));
    }
}
