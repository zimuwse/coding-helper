package org.muzi.open.helper.util;

import org.apache.commons.net.util.Base64;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author: muzi
 * @time: 2018-05-25 19:37
 * @description:
 */
public class EncodingUtil {

    public static String toUnicode(String str) {
        char[] utfBytes = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            builder.append("\\u").append(hexB);
        }
        return builder.toString();
    }

    public static String decodeUnicode(String str) {
        int start = 0;
        int end;
        StringBuilder buffer = new StringBuilder();
        while (start > -1) {
            end = str.indexOf("\\u", start + 2);
            String charStr;
            if (end == -1) {
                charStr = str.substring(start + 2, str.length());
            } else {
                charStr = str.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(Character.toString(letter));
            start = end;
        }
        return buffer.toString();
    }

    public static String base64Decode(String s, String charset) throws Exception {
        return new String(Base64.decodeBase64(s.getBytes(charset)), charset);
    }

    public static String base64Encode(String s, String charset) throws Exception {
        return new String(Base64.encodeBase64(s.getBytes(charset)), charset);
    }

    public static String urlEncode(String s, String charset) throws Exception {
        return URLEncoder.encode(s, charset);
    }

    public static String urlDecode(String s, String charset) throws Exception {
        return URLDecoder.decode(s, charset);
    }


}
