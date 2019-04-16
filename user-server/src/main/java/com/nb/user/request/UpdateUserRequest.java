package com.nb.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description 修改用户请求对象
 * @author: fly
 * @date: 2018/11/21 15:36
 */
@Data
public class UpdateUserRequest {
    @NotNull(message = "主键不能为空")
    private Long userId;
    @NotNull(message = "姓名不能为空")
    private String realName;
    @NotNull(message = "电话不能为空")
    private String mobile;
    @NotNull(message = "地址不能为空")
    private String address;
    @NotNull(message = "工号不能为空")
    private String workNum;
}
