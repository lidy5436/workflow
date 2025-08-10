package com.example.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 */
@Data
public class PageResult<T> implements Serializable {

    // 总记录数
    private long total;

    // 当前页数据列表
    private List<T> list;

    // 当前页码
    private int pageNum;

    // 每页条数
    private int pageSize;

    // 总页数
    private int pages;
}
