package com.cpren.service.impl;

import com.cpren.dao.Child2Dao;
import com.cpren.service.TransactionChild2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionChild2Impl implements TransactionChild2 {

    @Autowired
    private Child2Dao child2Dao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        child2Dao.save();
        int i = 1/0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requires_new() {
        child2Dao.save();
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public void nested() {
        child2Dao.save();
        int i = 1/0;
    }
}
