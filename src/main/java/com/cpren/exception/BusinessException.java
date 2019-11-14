package com.cpren.exception;

/**
 * @author cdxu@iyunwen.com on 2018/2/8
 */
public class BusinessException extends BaseException {
    /**
     *
     */
    private static final long serialVersionUID = -3570377345875566546L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }
}
