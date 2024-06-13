package com.yangge.common.dto;


import com.yangge.common.constant.ApiResponseCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ApiResponse<T> {

    /**
     * 返回的数据
     */
    private T data;
    /**
     * 返回的code值
     */
    private Integer code = 0;
    /**
     * code值的描述
     */
    private String codeMessage;
    /**
     * 错误信息
     */
    private Map<String, String> errorMessage;
    /**
     * 返回的成功状态
     */
    private Boolean success = true;

    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setCode(ApiResponseCode.SUCCESS.getCode());
        response.setCodeMessage(ApiResponseCode.SUCCESS.getMessage());
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> error(Map<String, String> errors) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(null);
        response.setCode(ApiResponseCode.SERVICE_ERROR.getCode());
        response.setCodeMessage(ApiResponseCode.SERVICE_ERROR.getMessage());
        response.setErrorMessage(errors);
        response.setSuccess(false);
        return response;
    }

    public static <T> ApiResponse<T> error(String error) {
        Map<String, String> errors = new HashMap<>();
        errors.put(error, error);
        return error(errors);
    }


    public ApiResponse<T> error(String msg, T data) {
        this.setData(data);
        this.setSuccess(false);
        this.setCode(ApiResponseCode.SERVICE_ERROR.getCode());
        this.setCodeMessage(ApiResponseCode.SERVICE_ERROR.getMessage());
        return this;
    }

    public ApiResponse<T> error(Integer code, Map<String, String> errors) {
        this.setCode(code);
        this.setErrorMessage(errors);
        this.setData(data);
        this.setSuccess(false);
        return this;
    }
}