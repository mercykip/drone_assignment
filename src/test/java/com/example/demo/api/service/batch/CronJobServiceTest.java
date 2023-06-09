package com.example.demo.api.service.batch;

import com.example.demo.api.domain.Drones;
import com.example.demo.api.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CronJobServiceTest {
    @Mock
    private  DroneRepository droneRepository;
    @Mock
    private  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    @Test
    void monitorDroneBatteryLevel() {
        List<Drones> results = droneRepository.findAll();

    }
}