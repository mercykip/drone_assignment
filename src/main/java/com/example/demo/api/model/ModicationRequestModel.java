package com.example.demo.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ModicationRequestModel {
    @JsonProperty(value = "medication_models")
    private List<MedicationModel> medicationModels;


    @JsonProperty(value="drones_id")
    private DroneId dronesId;
}
