package com.nb.user.service;

import com.nb.user.model.StallApproval;
import com.nb.user.vo.StallApprovalVO;

import java.util.List;

/**
 * @author fly
 * @description
 * @date 2019/4/15 18:30
 **/
public interface StallApprovalService {

    /**
     * 保存审批信息
     *
     * @param stallApproval 审批信息
     */
    void saveStallApproval(StallApproval stallApproval);

    /**
     * 获取待审批任务
     *
     * @param userId 用户主键
     * @return 待审批任务
     */
    List<StallApprovalVO> queryByUserId(String userId);
}
