package com.pan.solver.util;

import org.springframework.util.DigestUtils;

public class MD5Util {

    public static String digest(String originString) {
        return new String(DigestUtils.md5Digest(originString.getBytes()));
    }

}
