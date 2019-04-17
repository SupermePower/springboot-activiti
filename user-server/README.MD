#springboot+activiti

##创建表
执行stall_approval.sql

##启动应用

##访问地址
localhost:7500/swagger-ui.html

##审批人ID

BDMID

ZONGJIANID

##获取当前执行阶段接口
不能通过swagger访问，可以直接通过浏览器访问
http://localhost:7500/approval/queryProPlan/32501
32501为processInstanceId	 审批流程实例主键可以在：获取当前人审批任务 接口中获取