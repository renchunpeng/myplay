package com.cpren.controller;

import cn.hutool.core.lang.Singleton;
import com.cpren.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cdxu@iyunwen.com on 2019/9/27
 */
@Controller
@RequestMapping("redis")
public class RedisTemplateController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("addUser")
    @ResponseBody
    public User addUser() throws JsonProcessingException {
        User user = Singleton.get(User.class);
        user.setUserName("renchunpeng");
        stringRedisTemplate.opsForValue().set("user",objectMapper.writeValueAsString(user));
        return user;
    }

    @RequestMapping("notice")
    public void delete() throws JsonProcessingException {
        stringRedisTemplate.convertAndSend("tellRobot", objectMapper.writeValueAsString(Singleton.get(User.class)));
    }
}
