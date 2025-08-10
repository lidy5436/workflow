package com.example.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程定义操作参数
 */
@Data
public class ProcessDefinitionOperateParam implements Serializable {

    // 流程定义ID
    private String processDefinitionId;

    // 是否包含实例，默认false
    private boolean includeInstances = false;

    // 生效时间，null表示立即生效
    private Date effectiveDate;
}
