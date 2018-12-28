package com.jubao.common.utils;

import com.jubao.common.exception.RRException;

import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

/**
 * token工具类
 * 
 */
public class TokenUtils {

    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new RRException("生成Token失败", e);
        }
    }

    /**
     * token是否过期
     * @param tokenExpireTime
     * @return
     */
    public static boolean isExpired(Date tokenExpireTime) {
        return tokenExpireTime.getTime() < System.currentTimeMillis();
    }

}
