package com.neu.csye6225.springdemo.util;

import com.neu.csye6225.springdemo.request.UserRequest;

public class Validator {

    public static boolean isUsernameValid(String username) {
        return username.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isUserRequestValid(UserRequest userRequest) {

        if(userRequest.getPassword()==null || userRequest.getUsername()==null || userRequest.getFirst_name()==null ||
            userRequest.getLast_name() == null || userRequest.getPassword().isEmpty() || userRequest.getUsername().isEmpty() ||
            userRequest.getFirst_name().isEmpty() || userRequest.getLast_name()==null || userRequest.getPassword().isBlank() ||
            userRequest.getFirst_name().isBlank() || userRequest.getLast_name().isBlank() || userRequest.getUsername().isBlank()
            || !isUsernameValid(userRequest.getUsername())) {
                return false;
        }
        return true;
    }
}
