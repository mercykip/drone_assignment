package com.example.demo.api.model.response;

import com.example.demo.api.model.MetaData;
import com.example.demo.api.model.response.PagenatedJsonResponse;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

public class JsonSetSuccessResponse {
    public static PagenatedJsonResponse setPagenatedResponse(int apiCode, String apiDescription, String trxId, Page<?> content) {
        PagenatedJsonResponse serviceResponse = new PagenatedJsonResponse();
        boolean success = true;
        boolean hasError = false;
        List emptyList = Collections.emptyList();
        MetaData meta = new MetaData();
        meta.setFirst(content.isFirst());
        meta.setLast(content.isLast());
        meta.setNumber(Integer.valueOf(content.getNumber()));
        meta.setNumberOfElements(Integer.valueOf(content.getNumberOfElements()));
        meta.setTotalPages(Integer.valueOf(content.getTotalPages()));
        meta.setTotalElements(Long.valueOf(content.getTotalElements()));
        meta.setSize(Integer.valueOf(content.getSize()));
        meta.setSort(content.getSort());
        serviceResponse.setSuccess(success);
        serviceResponse.setHas_error(hasError);
        serviceResponse.setApi_code(apiCode);
        serviceResponse.setApi_code_description(apiDescription);
        serviceResponse.setTrx_id(trxId);
        if (content.hasContent()) {
            serviceResponse.setData(content.getContent());
            serviceResponse.setMeta(meta);
        } else {
            serviceResponse.setData(emptyList);
            serviceResponse.setMeta(null);
        }
        return serviceResponse;
    }

    public static ApiResponse setResponse(int apiCode, String apiDescription,  Object content) {
        ApiResponse serviceResponse = new ApiResponse();


        serviceResponse.setApi_code(apiCode);
        serviceResponse.setMessage(apiDescription);
        serviceResponse.setData(content);
        return serviceResponse;
    }


}
