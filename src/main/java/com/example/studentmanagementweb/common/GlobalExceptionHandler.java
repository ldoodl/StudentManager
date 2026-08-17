package com.example.studentmanagementweb.common;

import com.example.studentmanagementweb.exception.BusinessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<String> handleRuntimeException(BusinessException e) {
        return Result.error(e.getCode(), "服务器内部错误：" + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleIllegalArg(MethodArgumentNotValidException e) {

        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.error(400, message);
    }
}
