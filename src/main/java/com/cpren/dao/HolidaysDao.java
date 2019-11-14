package com.cpren.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/9/3
 */
@Mapper
@Repository
public interface HolidaysDao {

    Integer checkHolidays(Date date);

    
}
