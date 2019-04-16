package com.nb.user.vo;


import com.nb.user.common.ErrorConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 响应对象
 * @author: fly
 * @date: 2018/11/21 14:58
 */
public class ResponseVO<T> {
    private int resultCode = ErrorConstant.SUCCESS;

    private String resultMessages = "";

    private T resultValue;

    public ResponseVO() {
    }

    public ResponseVO(int resultCode, String resultMessages) {
        this.resultCode = resultCode;
        this.resultMessages = resultMessages;
    }

    public ResponseVO(int resultCode, String resultMessages, T resultValue) {
        this.resultCode = resultCode;
        this.resultMessages = resultMessages;
        this.resultValue = resultValue;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessages() {
        return resultMessages;
    }

    public void setResultMessages(String resultMessages) {
        this.resultMessages = resultMessages;
    }

    public T getResultValue() {
        return resultValue;
    }

    public void setResultValue(T resultValue) {
        this.resultValue = resultValue;
    }

    public ResponseVO setResult(int resultCode, String resultMessages, T resultValue) {
        this.resultCode = resultCode;
        this.resultMessages = resultMessages;
        this.resultValue = resultValue;
        return this;
    }

    public ResponseVO setResult(int resultCode, String resultMessages) {
        this.resultCode = resultCode;
        this.resultMessages = resultMessages;
        return this;
    }

    private static ResponseVO.DefaultBuilder resultMessages(int resultCode, String resultMessages) {
        return new ResponseVO.DefaultBuilder(resultCode, resultMessages);
    }

    private static ResponseVO.DefaultBuilder success() {
        return resultMessages(ErrorConstant.SUCCESS, ErrorConstant.SUCCESS_MSG);
    }

    public static <T> ResponseVO<T> success(T resultValue) {
        ResponseVO.DefaultBuilder builder = success();
        return builder.build(resultValue);
    }

    public static <T> ResponseVO<T> success(T resultValue, String resultMessages) {
        ResponseVO.DefaultBuilder builder = resultMessages(ErrorConstant.SUCCESS, resultMessages);
        return builder.build(resultValue);
    }

    public static <T> ResponseVO<T> success(Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        ResponseVO.DefaultBuilder builder = success();
        return builder.build(result);
    }

    public static <E> ResponseVO<ListResult<List<E>>> success(List<E> list) {
        ListResult resultValue = ListResult.build(list);
        return ResponseVO.success(resultValue);
    }

    public static <E> ResponseVO<ListResult<List<E>>> success(List<E> list, String resultMessages) {
        ListResult resultValue = ListResult.build(list);
        return ResponseVO.success(resultValue, resultMessages);
    }

    public static <T> ResponseVO<T> error(int resultCode, String resultMessages, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        ResponseVO.DefaultBuilder builder = resultMessages(resultCode, resultMessages);
        return builder.build(result);
    }

    public static <T, E> ResponseVO<ListResult<List<E>>> error(int resultCode, String resultMessages) {
        List<E> listValue = new ArrayList<>();
        ListResult<List<E>> result = new ListResult<List<E>>(listValue);
        ResponseVO.DefaultBuilder builder = resultMessages(resultCode, resultMessages);
        return builder.build(result);
    }

    public interface ResponseBuilder {
        <T> ResponseVO<T> build(T messages);
    }

    private static class DefaultBuilder implements ResponseBuilder {
        private final int resultCode;
        private final String resultMessages;

        public DefaultBuilder(int resultCode, String resultMessages) {
            this.resultCode = resultCode;
            this.resultMessages = resultMessages;
        }

        @Override
        public <T> ResponseVO<T> build(T resultValue) {
            return new ResponseVO(this.resultCode, this.resultMessages, resultValue);
        }
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "resultCode=" + resultCode +
                ", resultMessages='" + resultMessages + '\'' +
                ", resultValue=" + resultValue +
                '}';
    }
}