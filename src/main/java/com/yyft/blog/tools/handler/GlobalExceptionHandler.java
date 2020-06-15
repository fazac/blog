package com.yyft.blog.tools.handler;

import com.yyft.common.model.BizException;
import com.yyft.common.model.Constants;
import com.yyft.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常 处理
     *
     * @param biz 自定义异常
     * @return Result
     */
    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException biz) {
        log.error("error={}", biz.getMessage());
        return Result.createBizError(Constants.UNKNOWN_EXCEPTION, biz.getMessage());
    }

    /**
     * 参数错误异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result handleParamException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult result = validException.getBindingResult();
            StringBuffer errorMsg = new StringBuffer();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    errorMsg.append(fieldError.getObjectName()).append(",").append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(",");
                });
                log.error("### 请求参数错误: {}", errorMsg);
            }
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.hasErrors()) {
                log.error("### 请求参数错误: {}", bindException.getAllErrors());
            }
        }
        return Result.createBizError(Constants.UNKNOWN_EXCEPTION, "参数错误");
    }

    /**
     * 未知异常
     *
     * @param ex 异常
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        log.error("未知异常 :", ex);
        return Result.createBizError(Constants.UNKNOWN_EXCEPTION, ex.getMessage());
    }
}
