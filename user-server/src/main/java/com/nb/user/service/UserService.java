package com.nb.user.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.nb.user.request.AddUserRequest;
import com.nb.user.request.QueryUserPageRequest;
import com.nb.user.request.UpdateUserRequest;
import com.nb.user.vo.ResponseVO;
import com.nb.user.vo.user.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description 用户业务接口
 * @author: fly
 * @date: 2018/11/20 14:35
 */
public interface UserService {

    /**
     * 获取用户信息根据用户主键
     *
     * @param userId 用户主键
     * @return 用户信息
     */
    UserVO queryUserById(Long userId);

    /**
     * 获取用户列表
     *
     * @return 用户列表
     * @throws Exception e
     */
    List<UserVO> queryUserList() throws Exception;

    /**
     * 分页获取用户信息
     *
     * @param request 分页请求对象
     * @return 用户信息
     */
    Page<UserVO> queryUserPage(QueryUserPageRequest request);

    /**
     * 新增用户
     *
     * @param request 用户信息
     * @throws Exception
     */
    void addUser(AddUserRequest request) throws Exception;

    /**
     * 修改用户信息
     *
     * @param request 用户信息
     * @return 处理结果
     */
    ResponseVO updateUser(UpdateUserRequest request);

    /**
     * 删除用户信息
     *
     * @param userId 用户主键
     * @return 处理结果
     */
    ResponseVO deleteUser(Long userId);

    /**
     * 根据地区获取用户信息
     *
     * @param addressName 地区名称
     * @return 用户信息
     */
    List<UserVO> queryUserByAddress(String addressName, HttpServletRequest request);
}
