package com.neu.csye6225.springdemo.controller;

import com.neu.csye6225.springdemo.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "HealthCheck REST Endpoint", description = "Returns 200 if application is Up and Running")
public class HealthCheckController {

    @ApiOperation(value = "Check the health api", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got the Status"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/healthz", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> healthCheck() {
        Map<String,String> map = new HashMap<>();
        map.put("Status", Constants.APPLICATION_RUNNING);
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "/healthh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> healthhCheck() {
        Map<String,String> map = new HashMap<>();
        map.put("Status", Constants.APPLICATION_RUNNING);
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "/healths", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> healthsCheck() {
        Map<String,String> map = new HashMap<>();
        map.put("Status", Constants.APPLICATION_RUNNING);
        return ResponseEntity.ok(map);
    }
}
