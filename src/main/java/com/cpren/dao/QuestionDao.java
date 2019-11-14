package com.cpren.dao;

import com.cpren.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/9/3
 */
@Mapper
@Repository
public interface QuestionDao {

    List<Question> queryAllQuestion();
}
