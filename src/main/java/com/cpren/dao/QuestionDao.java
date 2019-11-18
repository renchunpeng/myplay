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

    @Select("select a.*,b.answer from kn_question a left join kn_answer b on a.knowledge_id = b.knowledge_id where LENGTH(answer) > 300 limit 100")
    List<Question> queryQuestion();
}
