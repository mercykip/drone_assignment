package com.example.demo.api.model.response;

import lombok.Data;

@Data
public class ApiResponse {
    private int api_code;
    Object data;
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(int api_code, Object data, String message) {
        this.api_code = api_code;
        this.data = data;
        this.message = message;
    }
}
