package com.cpren.service.impl;

import com.cpren.service.TransactionChild1;
import com.cpren.service.TransactionChild2;
import com.cpren.service.TransactionParent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionParentImpl implements TransactionParent {

    @Autowired
    private TransactionChild1 transactionChild1;

    @Autowired
    private TransactionChild2 transactionChild2;

    /**
     * 父方法required，子方法required
     * 子方法1报错并向上传递
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void required_required() {
        try {
            transactionChild1.required();
        } catch (Exception e) {
            e.printStackTrace();
        }

        transactionChild2.required();
    }

    /**
     * 父方法required，子方法required_new
     * 子方法2报错并向上传递
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void required_requires_new() {
        transactionChild1.requires_new();

        transactionChild2.required();
    }

    /**
     * 父方法required，子方法nested
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void required_nested() {
        transactionChild1.required();

        try {
            transactionChild2.nested();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
