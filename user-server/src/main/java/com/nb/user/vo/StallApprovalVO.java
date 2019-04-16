package com.nb.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fly
 * @description 待审批
 * @date 2019/4/16 12:04
 */
@Getter
@Setter
@ToString
public class StallApprovalVO {
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "审批内容")
    private String msg;
    @ApiModelProperty(value = "审批任务ID")
    private String taskId;
    @ApiModelProperty(value = "审批实例ID")
    private String processInstanceId;
}
