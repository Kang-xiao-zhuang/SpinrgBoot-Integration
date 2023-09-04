package com.zhuang.exception;

import com.zhuang.domain.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public Result accessDeniedException() {
        return new Result(403, "用户权限不足！", null);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result serverException() {
        return new Result(500, "服务出现异常！", null);
    }
}