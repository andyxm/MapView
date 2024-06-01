package cn.mapview;

import androidx.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    @org.junit.Test
    public void aaa() {
        String md5String = "02EBEF8C56A7ADA3F1ED5E900E3143C0";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(md5String.getBytes());
            byte[] digest = md.digest();

            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            System.out.println(sb.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String md5String1 = getMD5String("1234");
        System.out.println("md5String1: " + md5String1);
    }


    public static String getMD5String(@NonNull String input){
        String ret = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] m = md5.digest();//加密
            ret = getHexString(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @NonNull
    public static String getHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder(40);
        for(byte b : bytes){
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
