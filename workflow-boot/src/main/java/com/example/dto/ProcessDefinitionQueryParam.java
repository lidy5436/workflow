package com.example.dto;

import lombok.Data;

/**
 * 流程定义查询参数
 * 用于封装流程定义列表的查询条件和分页信息
 */
@Data
public class ProcessDefinitionQueryParam {

    /**
     * 页码，默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页条数，默认10
     */
    private Integer pageSize = 10;

    /**
     * 流程名称模糊查询
     */
    private String nameLike;

    /**
     * 流程标识（key）精确查询
     */
    private String key;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 流程部署ID
     */
    private String deploymentId;

    /**
     * 是否挂起状态：true-挂起，false-激活，null-全部
     */
    private Boolean suspended;

    /**
     * 是否只查询最新版本，默认true
     */
    private boolean onlyLatestVersion = true;
}
