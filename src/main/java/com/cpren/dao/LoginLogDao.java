package com.cpren.dao;

import com.cpren.pojo.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/9/3
 */
@Mapper
@Repository
public interface LoginLogDao {

    @Select("select * from public_login_log limit 500000")
    List<LoginLog> getAll();
}
