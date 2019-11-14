package com.cpren.utils.yw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统工具类
 */
public class YWSystemUtils {

    private static final String OS_NAME = "os.name";

    private static final String WINDOWS = "Windows";

    private static final String LINUX = "Linux";

    private static final String DEFAULT_SPLIT = ",";

    private static final String[] IFCONFIG = {"ifconfig", "-a"};

    private static final String[] IPCONFIG = {"ipconfig", "/all"};

    private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*",
            Pattern.CASE_INSENSITIVE);

    /**
     * 禁止实例化
     */
    private YWSystemUtils() {

    }

    public static String getOSName() {
        return System.getProperty(OS_NAME);
    }

    public static String getIPAddress() {
        try {
            return String.valueOf(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMacAddress() {
        return getMacAddress(":", false, false);
    }

    /**
     * 获取mac地址
     *
     * @param split   多个mac之间分割符
     * @param replace 是否替换"-",true 替换 false 不替换
     * @param first   多个mac地址是否只取第一个,true是 false全部
     * @return 指定格式的mac
     */
    public static String getMacAddress(String split, boolean replace, boolean first) {
        String mac = null;

        if (getOSName().contains(LINUX)) {
            mac = getMacAddress(IFCONFIG, split, first);
        }

        if (getOSName().contains(WINDOWS)) {
            mac = getMacAddressForWindows();
            mac = mac == null || mac.trim().length() == 0 ? getMacAddress(IPCONFIG, split, first) : mac;
        }

        if (mac != null && replace) {
            mac = mac.replaceAll("\\-", "");
        }
        return mac;
    }

    private static String getMacAddressForWindows() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Set<String> getMacAddressByCommand(String[] commmand) {
        Process process = null;
        BufferedReader bufReader = null;
        try {
            process = Runtime.getRuntime().exec(commmand);
            bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Set<String> macSet = new HashSet<>();
            String line = null;
            while ((line = bufReader.readLine()) != null) {
                Matcher matcher = macPattern.matcher(line);
                if (matcher.matches()) {
                    macSet.add(matcher.group(1));
                }
            }
            return macSet;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static String getMacAddress(String[] commmand, String split, boolean first) {
        Set<String> macSet = getMacAddressByCommand(commmand);
        if (macSet == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator = macSet.iterator();
        int i = 0;
        split = split == null ? DEFAULT_SPLIT : split;
        while (iterator.hasNext()) {
            if (first) {
                stringBuilder.append(iterator.next());
                break;
            }

            if (i++ > 0) {
                stringBuilder.append(split);
            }
            stringBuilder.append(iterator.next());
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(String.format("os.name=%s", getOSName()));
        System.out.println(String.format("IPAddress=%s", getIPAddress()));
        System.out.println(String.format("MAC=%s", getMacAddress()));
    }
}
