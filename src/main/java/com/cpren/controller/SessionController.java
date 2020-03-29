package com.cpren.controller;

import com.cpren.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author cdxu@iyunwen.com on 2019/9/17
 */
@Controller
@RequestMapping(value = "/session")
public class SessionController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("destorySession")
    public void destorySession(HttpServletRequest request, HttpServletResponse response) {
        request.getSession();
        request.getSession().invalidate();
    }

    @GetMapping("create")
    @ResponseBody
    public void create(HttpServletRequest request, HttpSession session) {
        session.setAttribute("name", "任春鹏");
        User user = new User();
        user.setUserName("任春鹏");
        redisTemplate.opsForValue().set("name", user);
    }

    @GetMapping("find")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('/session/find')")
    public String find(HttpSession session) {
        return session.getAttribute("name").toString();
    }
}
