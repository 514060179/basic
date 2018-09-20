package com.simon.basics.componet.exception;

/**
 * 写入数据库异常（回滚）
 *
 * @author fengtianying
 * @date 2018/9/20 15:45
 */
public class SqlWriteException extends RuntimeException {

    public SqlWriteException(String message) {
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     * @param cause   根异常类（可以存入任何异常）
     */
    public SqlWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}

