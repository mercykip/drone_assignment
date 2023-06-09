package com.example.demo.api.service.batch;

import com.example.demo.api.domain.Drones;
import com.example.demo.api.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CronJobService {
    private static final Logger logger = LoggerFactory.getLogger(CronJobService.class);
    private final DroneRepository droneRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    public CronJobService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Scheduled(cron = "1 * * * * ?")
    public void monitorDroneBatteryLevel() {
        //get list of drones
        List<Drones> results = droneRepository.findAll();
        List<Integer> per = results.stream().map(Drones::getBatteryPercentage).toList();
        List<String> id = results.stream().map(Drones::getSerialNumber).toList();

        //loop through the list and add serial number and percentage to a map
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < results.size(); i++) {
            map.put(String.valueOf(id.get(i)), String.valueOf(per.get(i)));
        }

        //display the percentage and
        for (Map.Entry e : map.entrySet()) {
            logger.info(e.getKey() + " = " + e.getValue() + " at "+ formatter.format(LocalDateTime.now()));
        }


    }


}
