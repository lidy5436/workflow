package com.example.service.impl;

import com.example.dto.*;
import com.example.service.ProcessDefinitionService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程定义服务实现
 */
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public PageResult<ProcessDefinitionDTO> queryProcessDefinitions(ProcessDefinitionQueryParam param) {
        // 构建查询条件
        ProcessDefinitionQuery query = buildProcessDefinitionQuery(param);
        
        // 计算总数
        long total = query.count();
        
        // 分页查询
        List<ProcessDefinition> processDefinitions = query
                .listPage((param.getPageNum() - 1) * param.getPageSize(), param.getPageSize());
        
        // 转换为DTO
        List<ProcessDefinitionDTO> dtoList = convertToDTOList(processDefinitions);
        
        // 构建分页结果
        return buildPageResult(param, total, dtoList);
    }

    @Override
    public Result toggleSuspend(ProcessDefinitionOperateParam param) {
        // 查询流程定义
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(param.getProcessDefinitionId())
                .singleResult();
        
        if (definition == null) {
            return Result.error("流程定义不存在");
        }
        
        // 切换状态
        try {
            if (definition.isSuspended()) {
                // 激活流程定义
                repositoryService.activateProcessDefinitionById(
                        param.getProcessDefinitionId(), 
                        param.isIncludeInstances(), 
                        param.getEffectiveDate()
                );
                return Result.success("流程定义已激活");
            } else {
                // 挂起流程定义
                repositoryService.suspendProcessDefinitionById(
                        param.getProcessDefinitionId(),
                        param.isIncludeInstances(),
                        param.getEffectiveDate()
                );
                return Result.success("流程定义已挂起");
            }
        } catch (Exception e) {
            // 记录异常日志
            return Result.error("操作失败：" + e.getMessage());
        }
    }

    @Override
    public ProcessDefinitionDTO getProcessDefinitionById(String processDefinitionId) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        
        if (definition == null) {
            return null;
        }
        
        return convertToDTO(definition);
    }

    /**
     * 构建流程定义查询对象
     */
    private ProcessDefinitionQuery buildProcessDefinitionQuery(ProcessDefinitionQueryParam param) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        
        // 处理查询参数
        if (param.getNameLike() != null && !param.getNameLike().isEmpty()) {
            query.processDefinitionNameLike("%" + param.getNameLike() + "%");
        }
        if (param.getKey() != null && !param.getKey().isEmpty()) {
            query.processDefinitionKey(param.getKey());
        }
        if (param.getCategory() != null && !param.getCategory().isEmpty()) {
            query.processDefinitionCategory(param.getCategory());
        }
        // 只查询最新版本
        if (param.isOnlyLatestVersion()) {
            query.latestVersion();
        }
        // 排序
        query.orderByProcessDefinitionVersion().desc();
        
        return query;
    }

    /**
     * 转换为DTO列表
     */
    private List<ProcessDefinitionDTO> convertToDTOList(List<ProcessDefinition> processDefinitions) {
        List<ProcessDefinitionDTO> dtoList = new ArrayList<>();
        for (ProcessDefinition definition : processDefinitions) {
            dtoList.add(convertToDTO(definition));
        }
        return dtoList;
    }

    /**
     * 转换为DTO对象
     */
    private ProcessDefinitionDTO convertToDTO(ProcessDefinition definition) {
        ProcessDefinitionDTO dto = new ProcessDefinitionDTO();
        dto.setId(definition.getId());
        dto.setName(definition.getName());
        dto.setKey(definition.getKey());
        dto.setVersion(definition.getVersion());
        dto.setDeploymentId(definition.getDeploymentId());
        dto.setResourceName(definition.getResourceName());
        dto.setDiagramResourceName(definition.getDiagramResourceName());
        
        // 获取部署时间
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(definition.getDeploymentId())
                .singleResult();
        if (deployment != null) {
            dto.setDeploymentTime(deployment.getDeploymentTime());
        }
        
        dto.setSuspended(definition.isSuspended());
        dto.setCategory(definition.getCategory());
        return dto;
    }

    /**
     * 构建分页结果对象
     */
    private PageResult<ProcessDefinitionDTO> buildPageResult(
            ProcessDefinitionQueryParam param, long total, List<ProcessDefinitionDTO> dtoList) {
        
        PageResult<ProcessDefinitionDTO> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setList(dtoList);
        pageResult.setPageNum(param.getPageNum());
        pageResult.setPageSize(param.getPageSize());
        pageResult.setPages((int) (total + param.getPageSize() - 1) / param.getPageSize());
        
        return pageResult;
    }
}
