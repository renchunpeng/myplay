package com.cpren;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nTest {
    public static void main(String[] args) {
        final String baseName = "i18n/message";
        final String key = "系统管理";
        System.out.println("中国："+ ResourceBundle.getBundle(baseName,
                Locale.CHINA).getString(key));
        System.out.println("美国："+ResourceBundle.getBundle(baseName,
                Locale.US).getString(key));
    }
}
