package com.example.demo.api.specification;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private Date startDate;
    private Date endDate;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String key, String operation, Date startDate, Date endDate) {
        this.key = key;
        this.operation = operation;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
