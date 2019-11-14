package com.cpren.pojo;


import lombok.Data;

/**
* @author yyzhang@iyunwen.com
*/
@Data
public class Examquestion {

  private Long id;
  private Integer examDifficult;
  private String examQuestion;
  private Integer type;
  private String selectA;
  private String selectB;
  private String selectC;
  private String selectD;
  private String selectE;
  private String selectF;
  private String selectG;
  private String selectH;
  private String selectI;
  private String selectJ;
  private String rightAnswer;
  private String expound;
  private Integer status;
  private Long createUserId;
  private java.sql.Timestamp createTime;
  private Long updateUserId;
  private java.sql.Timestamp updateTime;
  private Long webId;
}
