package com.example.demo.api.service;

import com.example.demo.api.domain.Drones;
import com.example.demo.api.domain.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DroneService {
    Drones saveDrone(Drones drones);
    Medication loadMedication(Medication medication);
    Page<Drones> getDrone(Specification specification, PageRequest pageRequest);


}
