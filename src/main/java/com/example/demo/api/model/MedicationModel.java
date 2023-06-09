package com.example.demo.api.model;

import com.example.demo.api.domain.Drones;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MedicationModel {
    public static final String nameRegex = "^(?<=^|\\s)[\\w_-]+(?=\\s|$)";
    public static final String codeRegex = "^(?=[0-9_]*[A-Z])\\b[A-Z0-9_]+\\b";

    @NotBlank
    @Pattern(regexp = nameRegex, message = "The name provided is Invalid, please enter a valid name")
    @JsonProperty(value="name")
    private String name;

    @NotBlank(message = "Weight cannot be blank")
    @JsonProperty(value="weight")
    private Double weight;


    @NotBlank(message = "Code cannot be blank")
    @Pattern(regexp = codeRegex, message = "The name provided is Invalid, please enter a valid name")
    @JsonProperty(value="code")
    private String code;

    @NotBlank
    @JsonProperty(value="image")
    private String image;

}
