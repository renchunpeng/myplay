package com.cpren.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Child1Dao {

    @Select("insert into child1 VALUES(1,NOW(),'child1');")
    void save();
}
