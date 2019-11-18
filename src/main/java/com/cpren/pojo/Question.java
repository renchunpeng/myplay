package com.cpren.pojo;


import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyzhang@iyunwen.com on 2018/8/2
 */
@Data
public class Question implements Serializable {
    private static final long serialVersionUID = -3835838439360930980L;

    private Long id;

    private String question;

    private Integer type;

    private Long domainId;

    private Long knowledgeId;

    private Integer source;

    private Long hits;

    private Long useful;

    private Long useless;

    private Long webId;

    private Integer del;

    private Long createUserId;

    private Timestamp createTime;

    private Long updateUserId;

    private Timestamp updateTime;

    private String fields;

    private String answer;

}
