package com.neu.csye6225.springdemo.controller;

import com.neu.csye6225.springdemo.config.StatsdClient;
import com.neu.csye6225.springdemo.model.ProfilePic;
import com.neu.csye6225.springdemo.response.UserProfilePictureResponse;
import com.neu.csye6225.springdemo.service.UserProfilePicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final static Logger logger = LogManager.getLogger(UserProfilePicController.class);

    private ModelMapper modelMapper;

    private UserProfilePicService userProfilePicService;

    private StatsdClient statsDClient;

    public UserProfilePicController(ModelMapper modelMapper, @Qualifier("UserProfilePicServiceImpl") UserProfilePicService userProfilePicService,
                                    StatsdClient statsDClient) {
        this.modelMapper = modelMapper;
        this.userProfilePicService = userProfilePicService;
        this.statsDClient = statsDClient;

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

        logger.info("Api called to get user profile pic info");
        statsDClient.increment("get.v1.user.self.pic");
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

        logger.info("Api called to delete profile pic info");
        statsDClient.increment("delete.v1.user.self.pic");
        userProfilePicService.deleteProfilePic();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Upload User Profile Pic Information", response = UserProfilePictureResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got the User Profile Picture Information" , response = UserProfilePictureResponse.class),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfilePictureResponse> uploadUserProfilePic(@RequestParam("profilePic") MultipartFile multipartFile) throws IOException {

        logger.info("Api to upload user profile pic info");
        statsDClient.increment("post.v1.user.self.pic");
        byte[] imageData = multipartFile.getBytes();
        if(imageData.length==0) {
            logger.info("Image data length is 0 (ZERO)");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ProfilePic profilePic = userProfilePicService.uploadUserProfilePic( multipartFile.getInputStream(), multipartFile.getOriginalFilename());
        UserProfilePictureResponse userProfilePictureResponse = modelMapper.map(profilePic, UserProfilePictureResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfilePictureResponse);
    }
}
