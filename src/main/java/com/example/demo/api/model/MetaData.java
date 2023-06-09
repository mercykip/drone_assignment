package com.example.demo.api.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class MetaData {
    private boolean first;
    private boolean last;
    private Integer number;
    @JsonProperty(value="number_of_elements")
    private Integer numberOfElements;
    @JsonProperty(value="total_pages")
    private Integer totalPages;
    @JsonProperty(value="total_elements")
    private Long totalElements;
    private Integer size;
    private Sort sort;

    public MetaData(){

    }

    public MetaData(boolean first, boolean last, Integer number, Integer numberOfElements, Integer totalPages, Long totalElements, Integer size, Sort sort) {
        this.first = first;
        this.last = last;
        this.number = number;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.sort = sort;
    }
}

