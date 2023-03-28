package com.xiaolan.auto.activiti;


import com.xiaolan.ServiceAuthApplication;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = ServiceAuthApplication.class)
@RunWith(SpringRunner.class)
public class TestMPDemo1 {
    @Autowired(required = false)
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;


    //如果挂起则激活，如果激活，则挂起
    @Test
    public void suspendProcessInstance(){
        //获取流程定义对象
        List<ProcessDefinition> qingjia = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("qingjia").list();
        for(ProcessDefinition q :qingjia) {
            boolean suspended = q.isSuspended();
            if (suspended) {
                //第一个流程ID
                //第二个 是否激活
                //第三个 时间点
                repositoryService.activateProcessDefinitionById(q.getId(), true, null);
                System.out.println(q.getId() + "激活咯~");
            } else {
                repositoryService.suspendProcessDefinitionById(q.getId(), true, null);
                System.out.println(q.getId() + "挂起咯~");
            }
        }


    }
    //创建业务实例，指定BusinessKey
    @Test
    public void SetBusinessKey(){
        //参数一 是流程定义的Key 参数二是 业务标识
        ProcessInstance qingjia = runtimeService.startProcessInstanceByKey("qingjia", "10001");
        System.out.println(qingjia.getBusinessKey());

    }

    //查询已处理任务
    @Test
    public void findProcessedTaskList() {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee("lisi")
                .finished().list();
        for (HistoricTaskInstance historicTaskInstance : list) {
            System.out.println("流程实例id：" + historicTaskInstance.getProcessInstanceId());
            System.out.println("任务id：" + historicTaskInstance.getId());
            System.out.println("任务负责人：" + historicTaskInstance.getAssignee());
            System.out.println("任务名称：" + historicTaskInstance.getName());
        }

    }
    //处理当前任务
    @Test
    public void completTask(){
        Task zhangsan = taskService.createTaskQuery().taskAssignee("zhangsan").singleResult();
        //完成任务
        taskService.complete(zhangsan.getId());

    }

    @Test
    public void findPendingTaskList() {
        String Assignee = "zhangsan";
        List<Task> list = taskService.createTaskQuery().taskAssignee(Assignee).list();
        for (Task task : list){
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }

    }

    //启动流程控制
    @Test
    public void startUpProcess() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("qingjia");
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
        System.out.println("当前活动Id：" + processInstance.getActivityId());

    }

    @Test
    public void testdeployProcess(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia.bpmn20.xml")
                .addClasspathResource("process/myfilename-20230325154436518.png")
                .name("请假申请流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());

    }
}
