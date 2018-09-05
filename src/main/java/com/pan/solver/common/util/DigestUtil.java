package com.pan.solver.common.util;

import org.springframework.util.DigestUtils;

public class DigestUtil {

    public static String digest(String string) {
        return new String(DigestUtils.md5Digest(string.getBytes()));
    }

}
