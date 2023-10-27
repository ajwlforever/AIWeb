package com.yqx.ai.common;


import java.util.UUID;

public class CommonUtil {

    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
