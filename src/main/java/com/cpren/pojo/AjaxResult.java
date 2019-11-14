package com.cpren.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @describe 接口统一响应结果
 * @author cp.ren
 * @date 2019-08-29 15:46:28
 * @version V5.0
 **/
@Data
public class AjaxResult<T> implements Serializable {
    /**
     * 响应码
     */
    private int code;

    /**
     * 接口响应数据
     */
    private T data;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应消息参数
     */
    Object[] messageArgs;

    /**
     * 日志信息
     */
    private String debug;

    public AjaxResult(int code, T data, String message, Object[] messageArgs, String debug) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.messageArgs = messageArgs;
        this.debug = debug;
    }

    public static <T> AjaxResult<T> failed(int code, String message, Object[] args) {
        return new AjaxResult<>(code, null, message, args, null);
    }
}
