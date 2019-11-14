package com.cpren.pojo;


import lombok.Data;

/**
* @author yyzhang@iyunwen.com
*/
@Data
public class LoginLog {

  private Long id;
  private Long user_id;
  private String user_name;
  private java.sql.Timestamp login_time;
  private String ip;
  private String location;
  private Long web_id;

}
