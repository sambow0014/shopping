package com.xp.common.advice;

import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import com.xp.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//默认情况下拦截所有的controller请求
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResult> handleException(SPException e){
        return ResponseEntity.status(new ExceptionResult(e.getExceptionEnum()).getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
