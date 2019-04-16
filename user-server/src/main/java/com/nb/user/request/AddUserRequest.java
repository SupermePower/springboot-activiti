package com.nb.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description 新增用户请求对象
 * @author: fly
 * @date: 2018/11/21 15:08
 */
@Data
public class AddUserRequest {
    @NotNull(message = "账户不能为空")
    private String userName;
    @NotNull(message = "姓名不能为空")
    private String realName;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "电话不能为空")
    private String mobile;
    @NotNull(message = "地址不能为空")
    private String address;
    @NotNull(message = "工号不能为空")
    private String workNum;
}
