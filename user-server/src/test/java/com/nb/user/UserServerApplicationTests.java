package com.nb.user;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServerApplicationTests {

    @Test
    public void contextLoads() {
        long id = IdWorker.getId();
        System.out.println(id);
    }

    /**
     * 测试发布流程
     */
    @Test
    public void publishProcessTest() {
        // 核心对象（服务大管家）
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取repositoryService仓库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //获取流程发布对象
        DeploymentBuilder createDeployment = repositoryService.createDeployment();
        //上传资源
        createDeployment.addClasspathResource("processes/StallProcessChoose.bpmn");
//        createDeployment.addClasspathResource("StallProcessChoose.png");
        //发布
        Deployment deploy = createDeployment.deploy();
        //DeploymentEntity[id=1, name=null]
        System.out.println(deploy);
    }
}
