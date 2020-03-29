package com.cpren.controller;

import cn.hutool.core.codec.Base64;
import com.cpren.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/8/27
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping("/user")
    @ResponseBody
    public User getUser(ModelAndView modelAndView, HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(null != cookies) {
            for(int i=0;i<cookies.length;i++) {
                if(cookies[i].getName().equals("user1")){
                    String value = cookies[i].getValue();
                    String s = Base64.decodeStr(value);
                    System.out.println("cookie中的user信息:"+s);
                }
            }
        }
        modelAndView.addObject("name","rcp");
        User user = new User();
        user.setUserName("任春鹏-来自A系统");
        user.setAge("27");
        user.setSex("男");
        user.setAddress("南京市雨花台区小行路158号");
        user.setBirth(new Date());
        List<User> lists = new ArrayList<>();
        for(int i=0;i<5;i++) {
            lists.add(user);
        }
        System.out.println(user.toString());
        try {
            String string = objectMapper.writeValueAsString(lists);
            String encode = Base64.encode(string);
            Cookie cookie = new Cookie("user1", encode);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return user;
    }

}
