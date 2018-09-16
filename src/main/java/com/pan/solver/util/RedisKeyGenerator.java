package com.pan.solver.util;

public class RedisKeyGenerator {

    public static String genToken(String emailAddress) {
        return "user" + emailAddress + "Token";
    }

}
