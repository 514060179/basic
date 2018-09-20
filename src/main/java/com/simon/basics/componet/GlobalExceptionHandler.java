package com.simon.basics.componet;

import com.simon.basics.componet.exception.SqlWritePrerequisiteException;
import com.simon.basics.model.vo.ReturnParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

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

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ReturnParam httpRequestMethodNotSupportedExceptionHandler(Exception e) {
        logger.error(">>>> system ：请求方法错误 ", e);
        return ReturnParam.systemError("405 get/post 请求方法错误");
    }

    @ExceptionHandler(value = {ConstraintViolationException.class,MethodArgumentTypeMismatchException.class})
    public ReturnParam constraintViolationExceptionHandler(Exception e) {
        logger.error(">>>> system error： ", e);
        return ReturnParam.paramiolationException(e.getMessage());
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ReturnParam duplicateKeyExceptionHandler(Exception e) {
        logger.error(">>>> system error： ", e);
        return ReturnParam.repeatResource("系统异常！");
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ReturnParam dataIntegrityViolationExceptionHandler(Exception e) {
        logger.error(">>>> system error： ", e);
        return ReturnParam.illegalKeyIdException();
    }
    @ExceptionHandler(value = SqlWritePrerequisiteException.class)
    public ReturnParam sqlWritePrerequisiteExceptionnHandler(Exception e) {
        logger.error(">>>> system error： ", e);
        return ReturnParam.sqlWritePrerequisiteException();
    }

    @ExceptionHandler(value = Exception.class)
    public ReturnParam exceptionHandler(Exception e) {
        logger.error(">>>> system error： ", e);
        return ReturnParam.systemError("系统异常！");
    }
//    @ResponseStatus(value= HttpStatus.UNAUTHORIZED,reason="没有权限")
//    public ReturnParam unauthorizedHandler(Exception e) {
//        logger.error(">>>> system error： ", e);
//        return ReturnParam.systemError("系统异常！");
//    }


}
