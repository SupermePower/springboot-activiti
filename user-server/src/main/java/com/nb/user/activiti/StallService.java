package com.nb.user.activiti;

import com.nb.user.dao.StallApprovalMapper;
import com.nb.user.model.StallApproval;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author fly
 * @description
 * @date 2019/4/15 15:22
 */
@Service
@Slf4j
public class StallService {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Autowired
    private StallApprovalMapper stallApprovalMapper;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;

    @Autowired
    private ProcessEngineFactoryBean processEngine;

    @Resource
    private ProcessEngineConfiguration processEngineConfiguration;

    /**
     * 启动审批流程
     *
     * @param businessKey 业务主键
     */
    public void startProcess(String businessKey) {
        log.info("start process businessKey -> {}", businessKey);
        runtimeService.startProcessInstanceByKey("StallProcessChoose", businessKey);
        log.info("end process businessKey");
    }

    /**
     * 修改状态(service-task自动调用)
     *
     * @param delegateExecution 代理
     * @param status            状态
     */
    public void changeStatus(DelegateExecution delegateExecution, String status) {
        String processInstanceBusinessKey = delegateExecution.getProcessBusinessKey();
        StallApproval stallApproval = stallApprovalMapper.selectById(processInstanceBusinessKey);
        stallApproval.setStatus(status);
        stallApprovalMapper.updateById(stallApproval);
    }

    /**
     * 获取审批人-BDM(user-task自动调用)
     *
     * @param delegateExecution 执行实例的代理对象
     * @return BDM
     */
    public List<String> findApproverBDM(DelegateExecution delegateExecution) {
        //TODO 获取审批人主键
        return Arrays.asList("BDMID", "BDMID2") ;
    }

    /**
     * 获取审批人-总监(user-task自动调用)
     *
     * @param delegateExecution 执行实例的代理对象
     * @return 总监
     */
    public String findApproverZJ(DelegateExecution delegateExecution) {
        //TODO 获取审批人主键
        return "ZONGJIANID";
    }

    /**
     * 获取当前用待审批任务
     *
     * @param userId 用户主键
     * @return 任务集合
     */
    public List<Task> findTaskByUserId(String userId) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
    }

    /**
     * 审批
     *
     * @param taskId 任务主键
     * @param userId 审批人主键
     * @param audit  审批意见
     */
    public void completeTaskByUser(String taskId, String userId, String audit) {
        log.info("start complete task \n taskId -> {} \n userId -> {} \n audit -> {}", taskId, userId, audit);
        // 认领任务-审批
        taskService.claim(taskId, userId);
        Map<String, Object> var = new HashMap<>(2);
        var.put("audit", audit);
        var.put("discount", 0.06);
        taskService.complete(taskId, var);
        log.info("end complete task");
    }

    /**
     * 获取当前审批流程阶段图片
     *
     * @param response          响应
     * @param processInstanceId 审批实体主键
     * @throws IOException IO异常
     */
    public void getCurrentProcessImg(HttpServletResponse response, String processInstanceId) throws IOException {
        //获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        List<HistoricActivityInstance> highLightedActivitiList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<>();

        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitiList);

        for (HistoricActivityInstance tempActivity : highLightedActivitiList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }

        //中文显示的是口口口，设置字体就好了
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "宋体", "", null, 1.0);
        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity   审批流程实体
     * @param historicActivityInstances 审批实例
     * @return 需要高亮的线
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<>();
        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            // 得到节点定义的详细信息
                            .getActivityId());
            // 用以保存后需开始时间相同的节点
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        // 后续第一个节点
                        .get(j);
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        // 后续第二个节点
                        .get(j + 1);
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
}
