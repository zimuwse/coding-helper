package org.muzi.open.helper.util;

import java.security.MessageDigest;

/**
 * @author: muzi
 * @time: 2018-05-25 15:36
 * @description:
 */
public class MD5Util {

    public static String md5(String s, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] input = s.getBytes(charset);
        byte[] buff = md.digest(input);
        return bytesToHex(buff).toLowerCase();
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuilder md5str = new StringBuilder();
        int digital;
        for (byte aByte : bytes) {
            digital = aByte;
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }
}
