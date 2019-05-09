package com.springboot.demo.common;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseWrapper<String> handleException(Exception e) {
        // 统一走日志记录
        log.error("系统错误：", e);
        return ResponseWrapper.markCustom(RespStatusCode.SYSTEM_INNER_ERROR, RespStatusCode.SYSTEM_INNER_ERROR.getMsg(),
                null);
    }
}
