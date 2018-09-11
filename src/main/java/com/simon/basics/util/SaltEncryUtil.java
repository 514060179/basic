package com.simon.basics.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author fengtianying
 * @date 2018/9/11 15:44
 */
public class SaltEncryUtil {

    //加密方式默认MD5
    private static String algorithmName = "MD5";

    //遍历次数
    private static int hashIterations = 2;

    public static String getMD5SaltString(String salt,String password){
        return getMD5SaltString(algorithmName, hashIterations,password,salt);
    }

    public static String getMD5SaltString(String algorithmName,int hashIterations,String salt,String password){
        return new SimpleHash(algorithmName, password.toCharArray(),
                salt, hashIterations).toHex();
    }

    public static void main(String[] args) {
        System.out.println(getMD5SaltString("simon","123456"));
    }
}
