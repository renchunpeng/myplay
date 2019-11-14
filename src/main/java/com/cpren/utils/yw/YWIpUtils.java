package com.cpren.utils.yw;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IpUtils
 * @Description ip地址工具类
 * @Author 谷和金  hjgu@iyunwen.com
 * @Date 2018/8/23 17:02
 * @Version 1.0
 */
public class YWIpUtils {

    /**
     * 禁止实例化
     */
    private YWIpUtils() {

    }


    public static String getIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (YWStringUtils.isNotBlank(ip)) {
            String[] ipList = ip.split(",");
            String[] arr$ = ipList;
            int len$ = ipList.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                String ipAddr = arr$[i$];
                if (!"unknown".equals(ipAddr.trim())) {
                    ip = ipAddr.trim();
                    break;
                }
            }
        }

        return ip;
    }
}
