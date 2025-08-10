package com.example.controller;

import com.example.dto.ProcessDefinitionOperateParam;
import com.example.dto.ProcessDefinitionQueryParam;
import com.example.dto.Result;
import com.example.service.ProcessDefinitionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 流程定义管理
 */
@RestController
@RequestMapping("/flowable/process-definitions")
public class ProcessDefinitionController {

    @Resource
    private ProcessDefinitionService processDefinitionService;

    /**
     * 分页查询流程定义列表
     */
    @PostMapping("/page")
    public Result pageProcessDefinitions(@RequestBody ProcessDefinitionQueryParam param) {
        // 参数校验
        if (param.getPageNum() == null || param.getPageNum() < 1) {
            param.setPageNum(1);
        }
        if (param.getPageSize() == null || param.getPageSize() < 1 || param.getPageSize() > 100) {
            param.setPageSize(10);
        }
        return Result.success(processDefinitionService.queryProcessDefinitions(param));
    }

    /**
     * 激活/挂起流程定义
     */
    @PostMapping("/toggle-suspend")
    public Result toggleSuspend(@RequestBody ProcessDefinitionOperateParam param) {
        if (param.getProcessDefinitionId() == null || param.getProcessDefinitionId().isEmpty()) {
            return Result.error("流程定义ID不能为空");
        }
        return processDefinitionService.toggleSuspend(param);
    }
    /**
     * 查看流程定义的流程图
     */
    @GetMapping("/processDefinition/{processId}")
    public void viewProcessDefinitionDiagram(@PathVariable("processId") String processId, HttpServletResponse response) throws IOException {
        processDefinitionService.viewProcessDefinitionDiagram(response,processId);
    }
}
