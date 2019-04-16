package com.nb.user.service.impl;

import com.nb.user.activiti.StallService;
import com.nb.user.dao.StallApprovalMapper;
import com.nb.user.model.StallApproval;
import com.nb.user.service.StallApprovalService;
import com.nb.user.util.DozerBeanMapperUtil;
import com.nb.user.vo.StallApprovalVO;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fly
 * @description
 * @date 2019/4/15 18:31
 */
@Service
public class StallApprovalServiceImpl implements StallApprovalService {

    @Autowired
    private StallApprovalMapper stallApprovalMapper;

    @Autowired
    private StallService stallService;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 保存审批信息
     *
     * @param stallApproval 审批信息
     */
    @Override
    public void saveStallApproval(StallApproval stallApproval) {
        Integer insert = stallApprovalMapper.insert(stallApproval);
        if (insert == 1) {
            stallService.startProcess(stallApproval.getId().toString());
        }
    }

    /**
     * 获取待审批任务
     *
     * @param userId 用户主键
     * @return 待审批任务
     */
    @Override
    public List<StallApprovalVO> queryByUserId(String userId) {
        List<StallApprovalVO> stallApprovalList = new ArrayList<>();
        List<Task> taskByUserId = stallService.findTaskByUserId(userId);
        for (Task task : taskByUserId) {
            // 获取流程实例
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            // businessKey 发起流程是传递的主键
            String businessKey = processInstance.getBusinessKey();
            StallApproval stallApproval = stallApprovalMapper.selectById(businessKey);
            StallApprovalVO stallApprovalVO = DozerBeanMapperUtil.dozerBeanMapper(stallApproval, StallApprovalVO.class);
            stallApprovalVO.setTaskId(task.getId());
            stallApprovalVO.setProcessInstanceId(task.getProcessInstanceId());
            stallApprovalList.add(stallApprovalVO);
        }
        return stallApprovalList;
    }
}
