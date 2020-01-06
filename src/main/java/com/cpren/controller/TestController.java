package com.cpren.controller;

import com.cpren.dao.QuestionDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author cdxu@iyunwen.com on 2019/8/27
 */
@Controller
@Slf4j
public class TestController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuestionDao questionDao;

    @ResponseBody
    @RequestMapping("/testMap")
    public List<Map> renchunpeng() {
        List<Map> test = questionDao.test();
        for (Map map : test){
            System.out.println(map);
        }
        return test;
    }

    @PostMapping("/array")
    public String setValue(@RequestBody List<String> lists) {
        System.out.println(lists.toString());
        System.out.println(123);
        return "123";
    }
}
