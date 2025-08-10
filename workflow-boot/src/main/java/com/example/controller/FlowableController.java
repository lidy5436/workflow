package com.example.controller;

import com.example.service.FlowableService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/flowable" )
public class FlowableController {

    @Resource
    private FlowableService flowableService;

    /**
     * 查看流程定义的流程图
     */
    @GetMapping("/processDefinition")
    public void viewProcessDefinitionDiagram(@RequestParam("processId") String processId, HttpServletResponse response) throws IOException {
        flowableService.viewProcessDefinitionDiagram(response,processId);
    }

    /**
     * 查看流程实例的流程图
     */
    @GetMapping("/processInstance")
    public void viewProcessInstanceDiagram(@RequestParam("processInstanceId") String processInstanceId, HttpServletResponse response) throws IOException {
        flowableService.viewProcessInstanceDiagram(response,processInstanceId);
    }




}
