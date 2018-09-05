package com.simon.basics.componet;

import com.simon.basics.model.ReturnParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author fengtianying
 * @date 2018/9/4 15:28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ReturnParam paramExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e) {
        e.printStackTrace();
        logger.error(">>>> system error： ", e);
        return ReturnParam.missParam(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ReturnParam noHandlerFoundException(NoHandlerFoundException e) {
        e.printStackTrace();
        logger.error(">>>> system error： ", e);
        return ReturnParam.noHandlerFound(e.getRequestURL() + " Not Found");
    }

    @ExceptionHandler(value = Exception.class)
    public ReturnParam exceptionHandler(Exception e) {
        logger.error(">>>> system error： ", e);
        return ReturnParam.systemError("系统异常！");
    }
}
