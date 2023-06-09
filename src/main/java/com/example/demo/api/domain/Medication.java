package com.example.demo.api.domain;

import com.example.demo.api.model.MedicationModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "medication")
@Getter
@Setter

public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medication_id", nullable=false)
    private Integer medicationId;

    private String name;

    private Double weight;

    private String code;

    private String image;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drones_id")
    private Drones drones;

    public Medication() {
    }

    public Medication(String name) {
        this.name = name;
    }

    public Medication(String name, Double weight, String code, String image) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }

    public Medication(String name, Double weight, String code, String image, Drones drones) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.drones = drones;
    }


}
