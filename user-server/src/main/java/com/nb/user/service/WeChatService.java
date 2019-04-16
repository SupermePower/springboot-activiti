package com.nb.user.service;

import com.nb.user.model.UserEntity;
import com.nb.user.request.WeChatUserRequest;

/**
 * @description 微信业务
 * @author: fly
 * @date: 2019/2/12 16:57
 */
public interface WeChatService {

    /**
     * 获取微信用户登录token
     *
     * @param request 账户、密码
     * @return token
     */
    String getAccessToken(WeChatUserRequest request);

    void test1(UserEntity userEntity);
}
