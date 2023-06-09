package com.example.demo.api.model;

import com.example.demo.api.domain.Models;
import com.example.demo.api.domain.State;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneModel {
    @NotBlank
    @Size(max = 100, message = "Invalid number of characters, serial number should not exceed 100 characters")
    private String serialNumber;

    @NotNull
    private Models models;

    @NotNull
    @Max(value = 500,message = "The weight limit is above the allowed limit, please enter the correct weight")
    private Double weightLimit;

    @NotNull
    @Max(value = 100, message = "Invalid number, battery percentage should not exceed 100")
    private int batteryPercentage;

    @NotNull
    private State state;
}
