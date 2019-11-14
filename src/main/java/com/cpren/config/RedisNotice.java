package com.cpren.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cdxu@iyunwen.com on 2019/9/27
 */
@Slf4j
public class RedisNotice {

    public void handleMessage(String str) {
        System.out.println("接收到robot频道的消息:" + str);
    }
}
