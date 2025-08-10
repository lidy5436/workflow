package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlowableService {
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;


    /**
     * 获取流程定义的流程图
     */
    public void viewProcessDefinitionDiagram(HttpServletResponse response, String processId) throws IOException {
        // 设置响应内容类型
        response.setContentType("image/png");
        // 获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processId)
                .singleResult();
        if (ObjectUtil.isEmpty(processDefinition)) {
            throw new IllegalArgumentException("流程定义不存在:" + processId);
        }
        // 获取BPMN模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processId);
        // 获取流程图生成器
        ProcessEngineConfiguration config = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = config.getProcessDiagramGenerator();

        // 生成流程图输入流
        try (OutputStream os = response.getOutputStream();
             InputStream is = diagramGenerator.generateDiagram(
                     bpmnModel,
                     "png",
                     new ArrayList<>(),
                     new ArrayList<>(),
                     config.getActivityFontName(),
                     config.getLabelFontName(),
                     config.getAnnotationFontName(),
                     config.getClassLoader(),
                     1.0,
                     false
             )) {
            // 拷贝流
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
        }
    }

    /**
     * 查看流程实例的流程图
     */
    public void viewProcessInstanceDiagram(HttpServletResponse response, String processInstanceId) throws IOException {
        response.setContentType("image/png");
        // 获取流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (ObjectUtil.isEmpty(processInstance)) {
            throw new IllegalArgumentException("流程实例ID不存在:" + processInstanceId);
        }
        // 获取流程定义和BPMN模型
        String processDefinitionId = processInstance.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        // 获取当前活动节点
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        // 获取流程图生成器
        ProcessEngineConfiguration config = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = config.getProcessDiagramGenerator();
        // 生成高亮流程图
        try (OutputStream os = response.getOutputStream();
             InputStream inputStream = diagramGenerator.generateDiagram(
                     bpmnModel,
                     "png",
                     activeActivityIds,
                     new ArrayList<>(),
                     config.getActivityFontName(),
                     config.getLabelFontName(),
                     config.getAnnotationFontName(),
                     config.getClassLoader(),
                     1.0,
                     false
             )) {
            // 复制流数据
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }


    }

    /**
     * 部署流程定义
     */
    public String deployProcess() {
        System.out.println("流程引擎对象:" + processEngine);

        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("process/leave001.bpmn20.xml")
                .name("请假流程001")
                .deploy();
        System.out.println("部署ID:" + deployment.getId());
        return deployment.getId();
    }

    /**
     * 启动流程实例
     */
    public void startProcess() {
        String processId = "leave:1:78e3e9be-75dd-11f0-9fe9-00ff184c8267";
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId);

    }


}
