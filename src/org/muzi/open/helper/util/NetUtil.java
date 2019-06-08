package org.muzi.open.helper.util;


import java.net.InetAddress;
import java.util.regex.Pattern;

/**
 * @author: muzi
 * @time: 2018-05-22 16:54
 * @description:
 */
public class NetUtil {

    /**
     * is ip address
     *
     * @param ip
     * @return
     */
    public static boolean isIP(String ip) {
        if (StringUtil.isEmpty(ip))
            return false;
        String reg = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(ip).matches();
    }


    /**
     * is server port
     *
     * @param s
     * @return
     */
    public static boolean isPort(String s) {
        if (!StringUtil.isInteger(s))
            return false;
        int val = Integer.parseInt(s);
        return val > 0 && val < 65535;
    }

    /**
     * ping
     *
     * @param host
     * @return
     */
    public static boolean ping(String host) {
        try {
            return InetAddress.getByName(host).isReachable(3000);
        } catch (Exception e) {
            return false;
        }
    }
}

