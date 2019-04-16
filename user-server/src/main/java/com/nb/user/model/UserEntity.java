package com.nb.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 用户实体
 * @author: fly
 * @date: 2018/11/20 14:32
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
    @TableId(value = "user_id")
    private Long userId;
    @TableField(value = "real_name")
    private String realName;
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "password")
    private String password;
    @TableField(value = "mobile")
    private String mobile;
    @TableField(value = "address")
    private String address;
    @TableField(value = "work_num")
    private String workNum;
    @TableField(value = "is_delete")
    private Byte delete;
}
