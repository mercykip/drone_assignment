package com.example.demo.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneId {
    @JsonProperty(value = "id")
    private Integer id;
}
