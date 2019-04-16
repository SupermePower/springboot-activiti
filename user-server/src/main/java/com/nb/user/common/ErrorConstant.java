package com.nb.user.common;

/**
 * @description 系统处理码
 * @author: fly
 * @date: 2018/11/21 16:13
 */
public class ErrorConstant {
    public static final int SUCCESS = 200;
    public static final String SUCCESS_MSG = "SUCCESS";

    public static final int METHOD_EXCEPTION = 500;
    public static final String METHOD_EXCEPTION_MSG = "系统错误，请稍后重试";


    public static final int ARGUMENT_NOT_VALID = 400;
    public static final String ARGUMENT_NOT_VALID_MSG = "请求参数不合法";

    public static final int PLEASE_INSTALL_NEW_VERSION = 100;
    public static final String PLEASE_INSTALL_NEW_VERSION_MSG = "请下载新版本";

    public static final int USER_NULL_ERROR = 300000000;
    public static final String USER_NULL_ERROR_MSG = "用户信息不存在";
}
