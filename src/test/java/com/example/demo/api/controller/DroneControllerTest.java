package com.example.demo.api.controller;

import com.example.demo.api.domain.Drones;
import com.example.demo.api.service.DroneImpl;
import com.example.demo.api.service.DroneService;
import com.example.demo.api.specification.DronePredicate;
import com.example.demo.api.specification.SearchCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value= DroneController.class)
class DroneControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  DroneService droneService;

    public DroneControllerTest(MockMvc mockMvc, DroneService droneService) {
        this.mockMvc = mockMvc;
        this.droneService = droneService;
    }

    @Test
   private void registerDrone() throws Exception {

        Drones drone = new Drones("A54", 254.0, 45,  "IDLE", "Cruiserweight");

        Mockito.when(droneService.saveDrone(drone));

        String inputInJson= this.mapToJson(drone);

        String URI = "/drone/register";

        Mockito.when(droneService.saveDrone(Mockito.any(Drones.class))).thenReturn(drone);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();

        assertThat(outputInJson).isEqualTo(inputInJson);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());



    }


    /**
     * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
     */
    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}