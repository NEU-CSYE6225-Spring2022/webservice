package com.neu.csye6225.springdemo.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.neu.csye6225.springdemo.util.Constants;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private HealthCheckController healthCheckController;

    @Test
    public void healthCheck() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("Status", Constants.APPLICATION_RUNNING);
        mvc.perform(MockMvcRequestBuilders.get("/healthz").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Status").value(Constants.APPLICATION_RUNNING));
    }

    @Test
    public void healthCheck2() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("Status", Constants.APPLICATION_RUNNING);
        assertEquals(healthCheckController.healthCheck().getBody().get("Status"), map.get("Status"));
    }
}
