package com.simon.basics.componet.exception;

/**
 * @author fengtianying
 * @date 2018/9/20 16:18
 */
public class SqlWritePrerequisiteException extends RuntimeException {

    public SqlWritePrerequisiteException(String message) {
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     * @param cause   根异常类（可以存入任何异常）
     */
    public SqlWritePrerequisiteException(String message, Throwable cause) {
        super(message, cause);
    }
}
