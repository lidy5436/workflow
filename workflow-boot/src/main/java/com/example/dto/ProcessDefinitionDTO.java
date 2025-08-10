package com.example.dto;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程定义数据传输对象
 */
@Data
public class ProcessDefinitionDTO implements Serializable {

    // 流程定义ID
    private String id;

    // 流程名称
    private String name;

    // 流程标识
    private String key;

    // 版本号
    private Integer version;

    // 部署ID
    private String deploymentId;

    // 资源文件名
    private String resourceName;

    // 流程图文件名
    private String diagramResourceName;

    // 部署时间
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date deploymentTime;

    // 是否挂起
    private boolean suspended;

    // 流程分类
    private String category;
}
