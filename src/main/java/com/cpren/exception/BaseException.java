package com.cpren.exception;

/**
 * @author cdxu@iyunwen.com on 2018/2/8
 */
public abstract class BaseException extends RuntimeException {
    private static final long serialVersionUID = -8899354027216514252L;

    private int code;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
