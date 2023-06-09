package com.example.demo.api.controller;

import com.example.demo.api.domain.Drones;
import com.example.demo.api.domain.Medication;
import com.example.demo.api.model.DroneModel;
import com.example.demo.api.model.ModicationRequestModel;
import com.example.demo.api.model.response.ApiResponse;
import com.example.demo.api.model.response.JsonSetSuccessResponse;
import com.example.demo.api.model.response.PagenatedJsonResponse;
import com.example.demo.api.model.response.SetErrorResponse;
import com.example.demo.api.service.DroneService;
import com.example.demo.api.specification.DronePredicate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.api.DroneUtils.filterRequestParams;
import static com.example.demo.api.model.response.JsonSetSuccessResponse.setResponse;

@RestController
@RequestMapping(value = "/drone")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerDrone(@RequestBody @Valid DroneModel droneModel) {
        try {
            List<Drones> results = droneService.getDrone(new DronePredicate(droneModel.getSerialNumber()), PageRequest.of(0, 1, Sort.Direction.DESC, "dronesId")).getContent();
            if (!results.isEmpty()) {
                ApiResponse response = SetErrorResponse.setResponse(HttpStatus.BAD_REQUEST.value(), "Dron registration failed, drone is registered", null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Drones drone = new Drones(droneModel.getSerialNumber(), droneModel.getWeightLimit(), droneModel.getBatteryPercentage(), droneModel.getBatteryPercentage() < 25 ? "IDLE" : droneModel.getState().getName(), droneModel.getModels().getName());

            Drones droneDb = droneService.saveDrone(drone);
            ApiResponse response = setResponse(HttpStatus.OK.value(), "Successful drone registration", droneDb);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse response = setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Drone Registration Failed", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/loadDrone")
    public ResponseEntity<Object> loadMedicationToDrone(@RequestBody @Valid ModicationRequestModel medicationModel) {
        try {
            Double medicineWeight, droneWeight;
            List<Drones> results = droneService.getDrone(new DronePredicate(medicationModel.getDronesId().getId()), PageRequest.of(0, 1, Sort.Direction.DESC, "dronesId")).getContent();
            if (results.isEmpty()) {
                ApiResponse response = SetErrorResponse.setResponse(HttpStatus.NOT_FOUND.value(), "Drone not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            medicineWeight = medicationModel.getMedicationModels().get(0).getWeight();
            droneWeight = results.get(0).getMedication().stream().map(Medication::getWeight).mapToDouble(Double::doubleValue).sum();

            if ((droneWeight + medicineWeight) > 500) {
                ApiResponse response = SetErrorResponse.setResponse(HttpStatus.BAD_REQUEST.value(), "Drone weight is above the allowed limit, please select another medicine with a smaller weight", null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Medication medication = new Medication(medicationModel.getMedicationModels().get(0).getName(),
                    medicationModel.getMedicationModels().get(0).getWeight(),
                    medicationModel.getMedicationModels().get(0).getCode(),
                    medicationModel.getMedicationModels().get(0).getImage(), new Drones(medicationModel.getDronesId().getId()));

            medication = droneService.loadMedication(medication);
            ApiResponse response = setResponse(HttpStatus.OK.value(), "Medication Loaded Successfully", medication);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Drone Registration Failed", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/availableDrones")
    public ResponseEntity<?> checkAvailableDrones(HttpServletRequest request,
                                                  @RequestParam(value = "page", required = false) String page,
                                                  @RequestParam(value = "page_size", required = false) String pageSize,
                                                  @RequestParam(value = "state", required = false) String state) {
        try {
            System.out.println("available Drones");
            List<String> unknownParams = filterRequestParams(request, Arrays.asList("page", "page_size", "id", "state"));
            if (!unknownParams.isEmpty()) {
                // get all errors
                String apiDesc = unknownParams.stream().map(x -> "'" + x.toUpperCase() + "'").collect(Collectors.joining(", ")) + " : Not valid Parameters";
                ApiResponse response = new ApiResponse(apiDesc);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            int pageNo = (page != null && Integer.valueOf(page) > 0) ? Integer.valueOf(page) - 1 : 0;
            int pageRequestSize = (pageSize != null && Integer.valueOf(pageSize) > 0) ? Integer.valueOf(pageSize) : Integer.MAX_VALUE;
            Page<Drones> results = droneService.getDrone(new DronePredicate("IDLE"), PageRequest.of(pageNo, pageRequestSize, Sort.Direction.DESC, "dronesId"));

            PagenatedJsonResponse response = JsonSetSuccessResponse.setPagenatedResponse(HttpStatus.OK.value(), results.isEmpty() ? "Drone not Found" : "List of Available  Drone Found", null, results);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse response = setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Drone Registration Failed", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/loadedMedication")
    public ResponseEntity<?> checkLoadedMedication(HttpServletRequest request,
                                                   @RequestParam(value = "page", required = false) String page,
                                                   @RequestParam(value = "page_size", required = false) String pageSize,
                                                   @RequestParam(value = "id", required = false) Integer id) {
        try {
            List<String> unknownParams = filterRequestParams(request, Arrays.asList("page", "page_size", "id"));
            if (!unknownParams.isEmpty()) {
                // get all errors
                String apiDesc = unknownParams.stream().map(x -> "'" + x.toUpperCase() + "'").collect(Collectors.joining(", ")) + " : Not valid Parameters";
                ApiResponse response = new ApiResponse(apiDesc);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            int pageNo = (page != null && Integer.valueOf(page) > 0) ? Integer.valueOf(page) - 1 : 0;
            int pageRequestSize = (pageSize != null && Integer.valueOf(pageSize) > 0) ? Integer.valueOf(pageSize) : Integer.MAX_VALUE;
            Page<Drones> results = droneService.getDrone(new DronePredicate(id), PageRequest.of(pageNo, pageRequestSize, Sort.Direction.DESC, "dronesId"));

            PagenatedJsonResponse response = JsonSetSuccessResponse.setPagenatedResponse(HttpStatus.OK.value(), results.isEmpty() ? "Drone not Found" : "List of Available  Drone Found", null, results);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error checking Loading Medication", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/checkPercentage")
    public ResponseEntity<?> checkBatteryPercentage(HttpServletRequest request, @RequestParam(value = "drone_id", required = false) Integer id) {
        try {

            ApiResponse response;
            /// Check For invalid params
            List<String> unknownParams = filterRequestParams(request, Arrays.asList("drone_id"));
            if (!unknownParams.isEmpty()) {
                // get all errors
                String apiDesc = unknownParams.stream().map(x -> "'" + x.toUpperCase() + "'").collect(Collectors.joining(", ")) + " : Not valid Parameters";
                response = new ApiResponse(apiDesc);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Page<Drones> results = droneService.getDrone(new DronePredicate(id), PageRequest.of(0, 1, Sort.Direction.DESC, "dronesId"));
            if (results.isEmpty()) {
                response = new ApiResponse("The battery percentage is  " + results.getContent().get(0).getBatteryPercentage().toString() + "%");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response = new ApiResponse("The battery percentage is  " + results.getContent().get(0).getBatteryPercentage().toString() + "%");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error checking battery percentage", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
