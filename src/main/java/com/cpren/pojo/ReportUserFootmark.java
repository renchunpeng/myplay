package com.cpren.pojo;

import lombok.Data;

/**
 * @describe
 * @author cp.ren
 * @date 2019-08-08 13:54:27
 * @version V5.0
 **/
@Data
public class ReportUserFootmark {

    private String searchDate;

    private Long userId;

    private String type;

    private String knowledgeId;

    private String question;

    private String  activeType;

    private String name;

    private String nickName;

    private String role;

    private String cityName;

    private String fieldName;

    /**
     * 新报表增加的字段
     */

    private String domainType;
}
