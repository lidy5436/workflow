package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 通用API响应结果封装类
 * 统一接口返回格式，包含状态码、提示信息和业务数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 成功状态码 */
    public static final int SUCCESS_CODE = 200;
    
    /** 失败状态码 */
    public static final int ERROR_CODE = 500;
    
    /** 未授权状态码 */
    public static final int UNAUTHORIZED_CODE = 401;
    
    /** 禁止访问状态码 */
    public static final int FORBIDDEN_CODE = 403;
    
    /** 资源不存在状态码 */
    public static final int NOT_FOUND_CODE = 404;

    /** 状态码：200-成功，其他-失败 */
    private int code;

    /** 提示信息 */
    private String msg;

    /** 业务数据 */
    private T data;

    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 成功响应（带自定义消息）
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(SUCCESS_CODE, msg, data);
    }

    /**
     * 成功响应（无数据）
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "操作成功", null);
    }

    /**
     * 失败响应（带错误消息）
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ERROR_CODE, msg, null);
    }

    /**
     * 失败响应（带错误码和消息）
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 未授权响应
     */
    public static <T> Result<T> unauthorized(String msg) {
        return new Result<>(UNAUTHORIZED_CODE, msg, null);
    }

    /**
     * 资源不存在响应
     */
    public static <T> Result<T> notFound(String msg) {
        return new Result<>(NOT_FOUND_CODE, msg, null);
    }
}
