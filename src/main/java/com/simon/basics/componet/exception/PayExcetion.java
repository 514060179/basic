package com.simon.basics.componet.exception;

/**
 * @author fengtianying
 * @date 2018/10/15 15:20
 */
public class PayExcetion  extends RuntimeException {

    public PayExcetion(String message) {
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     * @param cause   根异常类（可以存入任何异常）
     */
    public PayExcetion(String message, Throwable cause) {
        super(message, cause);
    }
}
