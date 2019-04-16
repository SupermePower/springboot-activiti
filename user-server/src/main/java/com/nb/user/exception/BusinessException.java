package com.nb.user.exception;

/**
 * @description 业务异常
 * @author: fly
 * @date: 2019/1/3 12:28
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code = 200;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }
}
