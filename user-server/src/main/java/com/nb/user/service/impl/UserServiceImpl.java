package com.nb.user.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.nb.user.common.CommonCondition;
import com.nb.user.common.ErrorConstant;
import com.nb.user.dao.UserMapper;
import com.nb.user.helper.AsyncTest;
import com.nb.user.idworker.IdWorker;
import com.nb.user.model.UserEntity;
import com.nb.user.request.AddUserRequest;
import com.nb.user.request.QueryUserPageRequest;
import com.nb.user.request.UpdateUserRequest;
import com.nb.user.service.UserService;
import com.nb.user.service.WeChatService;
import com.nb.user.util.DozerBeanMapperUtil;
import com.nb.user.vo.ResponseVO;
import com.nb.user.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private AsyncTest asyncTest;


    @Resource(name = "weChatServiceImpl")
    private WeChatService impl;

    @Value("${message-prompt}")
    private boolean messagePrompt;

    /**
     * 获取用户信息根据用户主键
     *
     * @param userId 用户主键
     * @return 用户信息
     */
    @Override
    public UserVO queryUserById(Long userId) {
        String s = null;
        s.toString();
        UserEntity userEntity = userMapper.findById(userId);
        if (userEntity == null || userEntity.getDelete() == 1) {
            return null;
        }
        return DozerBeanMapperUtil.dozerBeanMapper(userEntity, UserVO.class);
    }

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @Override
    public List<UserVO> queryUserList() {
        System.out.println(messagePrompt);
        if (messagePrompt) {
            List<UserEntity> userEntities = userMapper.selectList(CommonCondition.getCommonCondition().getCommonWrapper());
            return getUserVOS(userEntities);
        }
        return null;
    }

    /**
     * 分页获取用户信息
     *
     * @param request 分页请求对象
     * @return 用户信息
     */
    @Override
    public Page<UserVO> queryUserPage(QueryUserPageRequest request) {
        Page<UserVO> page = new Page<>(request.getCurrent(), request.getSize(), "user_id", true);
        List<UserEntity> userEntities = userMapper.selectPage(page, getQueryUserPageCondition(request));
        List<UserVO> userVOList = getUserVOS(userEntities);
        return page.setRecords(userVOList);
    }

    /**
     * 新增用户
     *
     * @param request 用户信息
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public void addUser(AddUserRequest request) {
        UserEntity userEntity = DozerBeanMapperUtil.dozerBeanMapper(request, UserEntity.class);
        userEntity.setUserId(idWorker.nextId());
        userMapper.insert(userEntity);
    }

    /**
     * 修改用户信息
     *
     * @param request 用户信息
     */
    @Override
    public ResponseVO updateUser(UpdateUserRequest request) {
        UserEntity userEntity = userMapper.selectById(request.getUserId());
        if (userEntity == null || userEntity.getDelete() == 1) {
            return new ResponseVO().setResult(ErrorConstant.USER_NULL_ERROR, ErrorConstant.USER_NULL_ERROR_MSG);
        }
        userEntity = DozerBeanMapperUtil.dozerBeanMapper(request, UserEntity.class);
        userMapper.updateById(userEntity);
        return new ResponseVO();
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户主键
     */
    @Override
    public ResponseVO deleteUser(Long userId) {
        UserEntity userEntity = userMapper.selectById(userId);
        if (userEntity == null || userEntity.getDelete() == 1) {
            return new ResponseVO().setResult(ErrorConstant.USER_NULL_ERROR, ErrorConstant.USER_NULL_ERROR_MSG);
        }
        userEntity.setDelete((byte) 1);
        userMapper.updateById(userEntity);
        return new ResponseVO();
    }

    /**
     * 用户信息
     *
     * @param addressName 地区名称
     * @return 用户信息
     */
    @Override
    public List<UserVO> queryUserByAddress(String addressName, HttpServletRequest request) {
        List<UserVO> userList = new LinkedList<>();
        userMapper.selectList(new EntityWrapper<UserEntity>()
                .like("address", addressName, SqlLike.RIGHT))
                .forEach(userEntity -> {
                    UserVO userVO = DozerBeanMapperUtil.dozerBeanMapper(userEntity, UserVO.class);
                    userList.add(userVO);
                });
        userMapper.selectList(new EntityWrapper<UserEntity>()
                .like("address", addressName, SqlLike.RIGHT))
                .forEach(userEntity -> {
                    UserVO userVO = DozerBeanMapperUtil.dozerBeanMapper(userEntity, UserVO.class);
                    userList.add(userVO);
                });
        return userList;
    }

    /**
     * 获取分页对象列表响应对象
     *
     * @param userEntities 用户实体
     * @return 响应对象列表
     */
    private List<UserVO> getUserVOS(List<UserEntity> userEntities) {
        List<UserVO> userVOList = new ArrayList<>();
        userEntities.forEach(userEntity -> {
            UserVO userVO = DozerBeanMapperUtil.dozerBeanMapper(userEntity, UserVO.class);
            userVOList.add(userVO);
        });
        return userVOList;
    }


    /**
     * 获取分页查询用户信息条件对象
     *
     * @param request 请求对象
     * @return 条件对象
     */
    private EntityWrapper<UserEntity> getQueryUserPageCondition(QueryUserPageRequest request) {
        return getUserEntityEntityWrapper(request.getMobile(), request.getWorkNum(), request.getRealName());
    }

    /**
     * 拼接查询条件
     *
     * @param mobile   电话
     * @param workNum  工号
     * @param realName 姓名
     * @return 查询条件
     */
    private EntityWrapper<UserEntity> getUserEntityEntityWrapper(String mobile, String workNum, String realName) {
        EntityWrapper<UserEntity> queryUserCondition = new EntityWrapper<>();
        if (mobile != null && !"".equals(mobile)) {
            queryUserCondition.eq("mobile", mobile);
        }
        if (workNum != null && !"".equals(workNum)) {
            queryUserCondition.eq("work_num", workNum);
        }
        if (realName != null && !"".equals(realName)) {
            queryUserCondition.like("real_name", realName);
        }
        queryUserCondition.eq("is_delete", 0);
        return queryUserCondition;
    }
}
