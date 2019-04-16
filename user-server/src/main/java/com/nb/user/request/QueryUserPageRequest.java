package com.nb.user.request;

import lombok.Data;

/**
 * @description 分页获取用户信息请求对象
 * @author: fly
 * @date: 2018/11/21 13:32
 */
@Data
public class QueryUserPageRequest {
    private Integer current;
    private Integer size;
    private String mobile;
    private String realName;
    private String workNum;
}
