package com.example.demo.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "drones")
@Getter
@Setter
public class Drones {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "drones_id", nullable = false)
    private Integer dronesId;
    @Column
    private String serialNumber;
    @Column
    private Double weightLimit;
    @Column
    private Integer batteryPercentage;
    @Column
    private String state;
    @Column
    private String models;

    @JsonProperty(value = "is_deleted")
    @Column(name = "is_deleted")
    private boolean deleted;

    @OneToMany(mappedBy = "drones", fetch = FetchType.LAZY)
    private List<Medication> medication;

    public Drones() {
    }

    public Drones(String state) {
        this.state = state;
    }


    public Drones(int id, List<Medication> medication) {
        this.dronesId = id;
        this.medication = medication;
    }

    public Drones(Integer id) {
        this.dronesId = id;
    }


    public Drones(String serialNumber, Double weightLimit, Integer batteryPercentage, String state, String models) {
        this.serialNumber = serialNumber;
        this.weightLimit = weightLimit;
        this.batteryPercentage = batteryPercentage;
        this.state = state;
        this.models = models;
    }


    public Drones(List<Medication> medicationModels, Integer id) {
        this.medication = medicationModels;
        this.dronesId = id;
    }
}
