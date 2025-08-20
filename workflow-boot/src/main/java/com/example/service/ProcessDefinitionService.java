package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.image.ProcessDiagramGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * 流程定义服务接口
 */
public interface ProcessDefinitionService {
    /**
     * 分页查询流程定义
     * @param param 查询参数
     * @return 分页结果
     */
    PageResult<ProcessDefinitionDTO> queryProcessDefinitions(ProcessDefinitionQueryParam param);

    /**
     * 激活/挂起流程定义
     * @param param 操作参数
     * @return 操作结果
     */
    Result toggleSuspend(ProcessDefinitionOperateParam param);

    /**
     * 根据ID获取流程定义详情
     * @param processDefinitionId 流程定义ID
     * @return 流程定义详情
     */
    ProcessDefinitionDTO getProcessDefinitionById(String processDefinitionId);

    /**
     * 获取流程定义的流程图
     * @param response 结果
     * @param processId 流程定义ID
     * @throws IOException 异常
     */
    public void viewProcessDefinitionDiagram(HttpServletResponse response, String processId) throws IOException;

    /**
     * 部署流程定义
     * @param processDefinitionDTO 数据
     */
    void deployProcessDeployment(ProcessDefinitionDTO processDefinitionDTO);
}
