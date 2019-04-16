package com.nb.user.request;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @description
 * @author: fly
 * @date: 2019/1/10 14:18
 */
@Data
public class QueryByTimeRequest {
    private Timestamp time;
}
