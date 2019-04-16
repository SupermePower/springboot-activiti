package com.nb.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author fly
 * @description 审批任务请求对象
 * @date 2019/4/16 12:12
 */
@Setter
@Getter
@ToString
public class CompleteTaskRequest {
    @ApiModelProperty(value = "任务主键")
    @NotNull(message = "任务主键不能为空")
    private String taskId;
    @ApiModelProperty(value = "用户主键")
    @NotNull(message = "用户主键不能为空")
    private String userId;
    @ApiModelProperty(value = "审批结果")
    @NotNull(message = "审批结果不能为空")
    private String audit;
}
