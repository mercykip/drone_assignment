package com.example.demo.api.service;

import com.example.demo.api.domain.Drones;
import com.example.demo.api.domain.Medication;
import com.example.demo.api.repository.DroneRepository;
import com.example.demo.api.repository.MedicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DroneImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public DroneImpl(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public Drones saveDrone(Drones drones) {
        return droneRepository.save(drones);
    }

    @Override
    public Medication loadMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    @Override
    public Page<Drones> getDrone(Specification specification, PageRequest pageRequest) {
        return droneRepository.findAll(specification, pageRequest);
    }

}
