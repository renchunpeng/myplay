package com.cpren.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Child2Dao {

    @Select("insert into child2 VALUES(1,NOW(),'child2');")
    void save();
}
