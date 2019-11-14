package com.cpren.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/8/27
 */
@Controller
@Slf4j
public class TestController {

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/array")
    public String setValue(@RequestBody List<String> lists) {
        System.out.println(lists.toString());
        System.out.println(123);
        return "123";
    }
}
