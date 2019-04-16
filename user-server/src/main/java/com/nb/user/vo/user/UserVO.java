package com.nb.user.vo.user;

import lombok.Data;

/**
 * @description 用户响应对象
 * @author: fly
 * @date: 2018/11/21 12:10
 */
@Data
public class UserVO {
    private Long userId;
    private String realName;
    private String mobile;
    private String address;
    private String workNum;
}
