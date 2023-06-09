package com.example.demo.api.service;

import com.example.demo.api.domain.Drones;
import com.example.demo.api.domain.Medication;
import com.example.demo.api.repository.DroneRepository;
import com.example.demo.api.repository.MedicationRepository;
import com.example.demo.api.specification.DronePredicate;
import com.example.demo.api.specification.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DroneImplTest {
    @Mock
    private DroneRepository droneRepository;
    @Mock
    private MedicationRepository medicationRepository;
    @InjectMocks
    private DroneImpl droneImpl;

    @BeforeEach
    void setUp() {
        //initialize mock
        droneImpl = new DroneImpl(droneRepository,medicationRepository);
    }
    @Test
    void saveDrone() {
        Drones dron= new Drones("1234",240.0,24,"IDLE","Lightweight");
        droneImpl.saveDrone(dron);
        // captures the actual value of the student passed to the save method
        ArgumentCaptor<Drones> droneArgumentCaptor = ArgumentCaptor.forClass(Drones.class);
        verify(droneRepository).save(droneArgumentCaptor.capture());


        Drones capturedStudent = droneArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(dron);

    }

    @Test
    void loadMedication() {
        Medication medication= new Medication("Amoxil", 24.4,"UI1","", new Drones(1));

        droneImpl.loadMedication(medication);
        assertThat(medicationRepository.findById(1)).isNotNull();

    }

    @Test
    void getDrone() {
        Drones dron= new Drones("1234",240.0,24,"IDLE","Lightweight");
        Drones dron2= new Drones("1244",240.0,50,"LOADING","Lightweight");
        DronePredicate spec = new DronePredicate(String.valueOf(new SearchCriteria("state", ":", "IDLE")));
        Pageable pageable = PageRequest.of(0, 2);
        given(droneRepository.findAll(spec,pageable)).willReturn(new PageImpl<>(List.of(dron, dron2), pageable, 0));
        //when
        droneImpl.getDrone(spec, PageRequest.ofSize(2));
        //then
        verify(droneRepository).findAll(spec, pageable);
    }
}