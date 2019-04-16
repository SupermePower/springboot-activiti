package com.nb.user.exception;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * @author fly
 * @description
 * @date 2019/3/8 9:44
 **/
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus
    public void sqlExceptionHandler(SQLException e) {
        System.out.println("SQL异常---------------->" + e.getMessage());
        e.printStackTrace();
    }

    @ExceptionHandler
    @ResponseStatus
    public void badSqlGrammarExceptionHandler(BadSqlGrammarException e) {
        System.out.println("BadSqlGrammarException---------------->" + e.getMessage());
        System.out.println("-------------------------------------------------------");
        e.printStackTrace();
    }

    @ExceptionHandler
    @ResponseStatus
    public String nullPointerExceptionHandler(NullPointerException e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("java.lang.NullPointerException \n");
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            stringBuilder.append("    " + stackTraceElement + "\n");
        }
       return stringBuilder.toString();
    }
}