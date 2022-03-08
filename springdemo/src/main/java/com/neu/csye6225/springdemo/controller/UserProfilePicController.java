package com.neu.csye6225.springdemo.controller;

import com.neu.csye6225.springdemo.model.ProfilePic;
import com.neu.csye6225.springdemo.model.User;
import com.neu.csye6225.springdemo.response.UserProfilePictureResponse;
import com.neu.csye6225.springdemo.response.UserResponse;
import com.neu.csye6225.springdemo.service.UserProfilePicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/user/self/pic")
@Api(value = "User Profile Picture related REST Endpoint", description = "Apis for adding and updating user profile pic info")
public class UserProfilePicController {

    private ModelMapper modelMapper;

    private UserProfilePicService userProfilePicService;

    public UserProfilePicController(ModelMapper modelMapper, @Qualifier("UserProfilePicServiceImpl") UserProfilePicService userProfilePicService) {
        this.modelMapper = modelMapper;
        this.userProfilePicService = userProfilePicService;
    }

    @ApiOperation(value = "Get User Profile Pic Information", response = UserProfilePictureResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got the User Profile Picture Information" , response = UserProfilePictureResponse.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfilePictureResponse> getUserInfo() {

        ProfilePic profilePic = userProfilePicService.getUserProfilePic();
        UserProfilePictureResponse userProfilePictureResponse = modelMapper.map(profilePic, UserProfilePictureResponse.class);
        return ResponseEntity.ok(userProfilePictureResponse);
    }

    @ApiOperation(value = "Deleting User Profile Pic Information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got the User Profile Picture Information" , response = UserProfilePictureResponse.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteProfilePic() {

        userProfilePicService.deleteProfilePic();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Get User Profile Pic Information", response = UserProfilePictureResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got the User Profile Picture Information" , response = UserProfilePictureResponse.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfilePictureResponse> uploadUserProfilePic(@RequestParam("profilePic") MultipartFile multipartFile) throws IOException {

        byte[] imageData = multipartFile.getBytes();
        if(imageData.length==0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ProfilePic profilePic = userProfilePicService.uploadUserProfilePic( multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        UserProfilePictureResponse userProfilePictureResponse = modelMapper.map(profilePic, UserProfilePictureResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfilePictureResponse);
    }
}
