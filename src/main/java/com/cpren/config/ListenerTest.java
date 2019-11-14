package com.cpren.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;

/**
 * @author cdxu@iyunwen.com on 2019/9/17
 */
//@WebListener
//@Slf4j
public class ListenerTest implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("请求销毁");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("请求创建");
        Map<String, String[]> parameterMap = sre.getServletRequest().getParameterMap();
        System.out.println(parameterMap);
    }
}
