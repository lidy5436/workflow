package com.example.service;

import com.example.dto.*;

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
}
