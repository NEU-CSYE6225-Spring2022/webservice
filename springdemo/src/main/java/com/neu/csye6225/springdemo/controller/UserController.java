package com.neu.csye6225.springdemo.controller;

import com.neu.csye6225.springdemo.model.User;
import com.neu.csye6225.springdemo.request.UserRequest;
import com.neu.csye6225.springdemo.response.UserResponse;
import com.neu.csye6225.springdemo.service.UserService;
import com.neu.csye6225.springdemo.util.Validator;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Api(value = "User related REST Endpoint", description = "Apis for adding and updating user info")
public class UserController {

    private ModelMapper modelMapper;

    private UserService userService;

    public UserController(ModelMapper modelMapper, @Qualifier("UserServiceImpl") UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ApiOperation(value = "Get User Information", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got the User Information" , response = UserResponse.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/user/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUserInfo() {

        User user = userService.getUserInfo();
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping( value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a User", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the User"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {

        if(!Validator.isUserRequestValid(userRequest) || isUsernameAlreadyExists(userRequest.getUsername()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        User user  = modelMapper.map(userRequest, User.class);
        user  = userService.createUser(user);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping(value = "user/self", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Updates a User", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated the User"),
            @ApiResponse(code = 400, message = "Bad request. Check the data once again"),
    })
    public ResponseEntity<Void> updateUser(@RequestBody UserRequest userRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(!Validator.isUserRequestValid(userRequest) || !userRequest.getUsername().equals(username)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        userService.updateUser(userRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private boolean isUsernameAlreadyExists(String username) {

        return userService.isUsernameAlreadyExists(username);
    }

}
