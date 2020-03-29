package com.cpren.service.impl;

import com.cpren.dao.Child1Dao;
import com.cpren.service.TransactionChild1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionChild1Impl implements TransactionChild1 {

    @Autowired
    private Child1Dao child1Dao;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        child1Dao.save();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requires_new() {
        child1Dao.save();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void nested() {
        child1Dao.save();
        int i = 1/0;
    }
}
