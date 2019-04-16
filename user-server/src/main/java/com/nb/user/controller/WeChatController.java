package com.nb.user.controller;

import com.nb.user.request.WeChatUserRequest;
import com.nb.user.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 微信空置器
 * @author: fly
 * @date: 2019/2/12 16:54
 */
@RestController
@RequestMapping("/weChat")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    @GetMapping(path = "/getAccessToken")
    public String getAccessToken(WeChatUserRequest request) {
        return weChatService.getAccessToken(request);
    }
}
