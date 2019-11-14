package com.cpren.advice;

import com.cpren.pojo.AjaxResult;
import org.jodconverter.office.OfficeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @describe 拦截一些常见的异常
 * @author cp.ren
 * @date 2019-09-26 14:17:42
 * @version V5.0
 **/
@ControllerAdvice
public class commonException {

    @ExceptionHandler({OfficeException.class})
    @ResponseBody
    public AjaxResult<?> handleOfficeException(OfficeException e) {
        return AjaxResult.failed(0,e.getMessage(),null);
    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseBody
    public AjaxResult<?> handleNullPointerException(NullPointerException e) {
        return AjaxResult.failed(0,e.toString(),e.getStackTrace());
    }
}
