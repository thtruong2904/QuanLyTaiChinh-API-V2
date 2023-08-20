package com.tranhuutruong.finance.build.utils;

import java.util.UUID;

public class RandomUtils {
    public static String getRandomVerifyCode()
    {
        return UUID.randomUUID().toString().substring(0,10);
    }
}
