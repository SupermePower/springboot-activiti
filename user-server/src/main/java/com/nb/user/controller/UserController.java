package com.nb.user.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.nb.user.common.ErrorConstant;
import com.nb.user.request.AddUserRequest;
import com.nb.user.request.QueryByTimeRequest;
import com.nb.user.request.QueryUserPageRequest;
import com.nb.user.request.UpdateUserRequest;
import com.nb.user.service.UserService;
import com.nb.user.vo.ResponseVO;
import com.nb.user.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @description 用户控制器
 * @author: fly
 * @date: 2018/11/20 14:31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender jms;

    @GetMapping("/send")
    public String send(){
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom("liying.fu@food-union.net");
        //接收者
        mainMessage.setTo("1483118244@qq.com");
        //发送的标题
        mainMessage.setSubject("嗨喽");
        //发送的内容
        mainMessage.setText("hello world");
        jms.send(mainMessage);
        return "1";
    }

    /**
     * 根据用户主键获取用户信息
     *
     * @param userId 用户主键
     * @return 用户信息
     */
    @GetMapping(path = "/queryUserById/{userId}")
    public ResponseVO<UserVO> queryUserById(@PathVariable Long userId) {
        ResponseVO<UserVO> responseVO = new ResponseVO<>();
        UserVO userVO = userService.queryUserById(userId);
        responseVO.setResult(200, "SUCCESS", userVO);
        return responseVO;
    }

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @GetMapping(path = "/queryUserList")
    public ResponseVO<List<UserVO>> queryUserList() {
        ResponseVO<List<UserVO>> responseVO = new ResponseVO<>();
        try {
            List<UserVO> userVOS = userService.queryUserList();
            responseVO.setResult(200, "SUCCESS", userVOS);
        } catch (Exception e) {
            e.printStackTrace();
            responseVO.setResult(ErrorConstant.METHOD_EXCEPTION, ErrorConstant.METHOD_EXCEPTION_MSG);
        }
        return responseVO;
    }

    /**
     * 分页获取用户对象
     *
     * @param request 分页信息
     * @return 用户信息
     */
    @GetMapping(path = "/queryUserPage")
    public ResponseVO<Page<UserVO>> queryUserPage(QueryUserPageRequest request, HttpServletRequest httpServletRequest) {
        ResponseVO<Page<UserVO>> responseVO = new ResponseVO<>();
        try {
            Page<UserVO> userVOPage = userService.queryUserPage(request);
            responseVO.setResult(200, "SUCCESS", userVOPage);
        } catch (Exception e) {
            e.printStackTrace();
            responseVO.setResult(ErrorConstant.METHOD_EXCEPTION, ErrorConstant.METHOD_EXCEPTION_MSG);
        }
        return responseVO;
    }

    /**
     * 新增用户
     *
     * @param request 用户信息
     * @return 新增结果
     */
    @PostMapping(path = "/addUser")
    public ResponseVO addUser(@RequestBody @Valid AddUserRequest request) {
        ResponseVO responseVO = new ResponseVO();
        try {
            userService.addUser(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseVO.setResult(200, "SUCCESS");
    }

    /**
     * 修改用户
     *
     * @param request 用户信息
     * @return 修改用户结果
     */
    @PutMapping(path = "/updateUser")
    public ResponseVO updateUser(@RequestBody @Valid UpdateUserRequest request) {
        return userService.updateUser(request);
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户主键
     * @return 删除结果
     */
    @DeleteMapping(path = "/deleteUser/{userId}")
    public ResponseVO deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    /**
     * 获取数据根据日期
     *
     * @param request 日期
     * @return 数据
     */
    @GetMapping(path = "/queryByTime")
    public ResponseVO queryByTime(QueryByTimeRequest request) {
        System.out.println(request.getTime());
        return new ResponseVO();
    }

    @GetMapping(path = "/queryUserByAddress/{addressName}")
    public ResponseVO<List<UserVO>> queryUserByAddress(@PathVariable String addressName, HttpServletRequest request) {
        ResponseVO<List<UserVO>> listResponseVO = new ResponseVO<>();
        List<UserVO> userVOList = userService.queryUserByAddress(addressName, request);
        listResponseVO.setResult(ErrorConstant.SUCCESS, ErrorConstant.SUCCESS_MSG, userVOList);
        return listResponseVO;
    }
}