package com.cpren.pojo;


import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yyzhang@iyunwen.com on 2018/8/2
 */
@Data
public class Holidays implements Serializable {
    private static final long serialVersionUID = -3835838439360930980L;

    private Long id;

    private int year;

    private String holidaysName;

}
