package com.nb.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nb.user.common.HttpHelper;
import com.nb.user.common.HttpResult;
import com.nb.user.dao.UserMapper;
import com.nb.user.model.UserEntity;
import com.nb.user.request.WeChatUserRequest;
import com.nb.user.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * @description 微信业务实现
 * @author: fly
 * @date: 2019/2/12 16:58
 */
@Service(value = "weChatServiceImpl")
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    private final String url = "https://api.weixin.qq.com/sns/jscode2session";

    private final String appid = "wxfc61b3daa2d95647";

    private final String secret = "b89e881163d43de3e7c5868dbb496d62";

    private final String grant_type = "authorization_code";


    /**
     * 获取微信用户token
     *
     * @param request 账户、密码
     * @return token
     */
    @Override
    public String getAccessToken(WeChatUserRequest request) {
        JSONObject rtn = null;
        try {
            String jsCode = "1341sadfa";
            String url = getUrl(this.url, appid, secret, grant_type, jsCode);
            HttpResult result = HttpHelper.getInstance().get(url, new HashMap<>());
            log.info("get openid result -> {} ->{}", result.getContent(), result.isOk());
            if (!result.isOk()) {
                return null;
            }
            rtn = JSONObject.parseObject(result.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn.getString("openid");
    }

    /**
     * 获取请求URL
     *
     * @param url
     * @param appid
     * @param secret
     * @param grant_type
     * @param jsCode
     * @return
     */
    private String getUrl(String url, String appid, String secret, String grant_type, String jsCode) {
        return url + "?appid=" + appid + "&secret=" + secret + "&js_code=" + jsCode + "&grant_type=" + grant_type;
    }


    @Transactional(rollbackFor = {Exception.class}, timeout = 2000, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void test1(UserEntity userEntity) {
        userEntity.setUserId(userEntity.getUserId() + 1);
        userEntity.setAddress("回滚到最初位置");
        userMapper.insert(userEntity);
        String s = null;
        s.equals("B");
    }

    @Autowired
    private UserMapper userMapper;
}
