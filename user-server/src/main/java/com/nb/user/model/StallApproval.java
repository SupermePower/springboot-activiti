package com.nb.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * @author fly
 * @description 审批
 * @date 2019/4/15 18:23
 */
@Setter
@Getter
@ToString
@TableName(value = "stall_approval")
public class StallApproval {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "process_initiator")
    private Long processInitiator;
    @TableField(value = "status")
    private String status;
    @TableField(value = "msg")
    private String msg;
    @TableField(value = "create_time")
    private Timestamp createTime;
    @TableField(value = "update_time")
    private Timestamp updateTime;
    @TableLogic
    @TableField(value = "is_delete")
    private Byte delete;
}
