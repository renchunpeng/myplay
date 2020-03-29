package com.cpren;

import com.cpren.service.TransactionParent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionTest {

    @Autowired
    private TransactionParent transactionParent;

    @Test
    public void start() {
        transactionParent.required_nested();
    }
}
