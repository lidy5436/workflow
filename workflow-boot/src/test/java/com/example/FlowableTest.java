package com.example;

import com.example.service.FlowableService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlowableTest {

    @Resource
    private FlowableService flowableService;

    // 流程部署
    @Test
    public void contextLoads() {
        flowableService.deployProcess();
    }

    // 启动流程实例
    @Test
    public void startProcess() {
        flowableService.startProcess();
    }
}
