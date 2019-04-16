package com.nb.user.helper;

import com.nb.user.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 异步测试
 * @author: fly
 * @date: 2019/1/21 19:00
 */
@Component
public class AsyncTest {


    @Autowired
    private UserConfig userConfig;

    @Async
    public void getHttpHeader(HttpServletRequest request, String addressName) {
        String userName = userConfig.getUserName();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(userName);
        System.out.println(addressName);
        System.out.println(request.getHeader("authorization"));
        System.out.println(request.getParameter("addressName"));
    }
}
