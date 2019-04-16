package com.nb.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.nb.user.model.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description 用户对象数据库映射
 * @author: fly
 * @date: 2018/11/20 14:37
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    @Select(value = "select * fro user where user_id = #{userId}")
    UserEntity findById(@Param("userId") Long userId);
}
