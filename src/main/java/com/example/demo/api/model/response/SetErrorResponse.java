package com.example.demo.api.model.response;

import com.example.demo.api.model.response.ApiResponse;

public class SetErrorResponse {
    public static ApiResponse setResponse(int code, String apiDescription, String trxId) {
        ApiResponse serviceResponse = new ApiResponse();
        serviceResponse.setApi_code(code);
        serviceResponse.setMessage(apiDescription);
        return serviceResponse;
    }
}
