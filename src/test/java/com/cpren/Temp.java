package com.cpren;

import com.cpren.dao.QuestionDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class Temp {
    @Autowired
    private QuestionDao questionDao;

    @Test
    public void renchunpeng() {
        List<Map> test = questionDao.test();
        for (Map map : test){
            System.out.println(map);
        }
    }
}
