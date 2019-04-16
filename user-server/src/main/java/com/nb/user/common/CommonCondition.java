package com.nb.user.common;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * @description 基本数据库条件
 * @author: fly
 * @date: 2018/11/21 14:44
 */
public class CommonCondition {

    private volatile static CommonCondition commonCondition;

    private CommonCondition() {
    }
    /**
     * 获取基本数据库条件
     *
     * @return 非删除对象
     */
    public Wrapper getCommonWrapper() {
        return new EntityWrapper().eq("is_delete", 0);
    }

    public static CommonCondition getCommonCondition() {
        if (commonCondition == null) {
            synchronized (CommonCondition.class) {
                if (commonCondition == null) {
                    commonCondition = new CommonCondition();
                }
            }
        }
        return commonCondition;
    }
}
