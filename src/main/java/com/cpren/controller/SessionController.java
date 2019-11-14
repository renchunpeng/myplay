package com.cpren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cdxu@iyunwen.com on 2019/9/17
 */
@Controller
public class SessionController {

    @RequestMapping("destorySession")
    public void destorySession(HttpServletRequest request, HttpServletResponse response) {
        request.getSession();
        request.getSession().invalidate();
    }

    @PostMapping("params")
    public void params(String name, String age) {
        System.out.println(name);
        System.out.println(age);
    }
}
