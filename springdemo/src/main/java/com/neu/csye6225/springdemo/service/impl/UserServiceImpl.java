package com.neu.csye6225.springdemo.service.impl;

import com.neu.csye6225.springdemo.model.User;
import com.neu.csye6225.springdemo.repository.UserRepository;
import com.neu.csye6225.springdemo.request.UserRequest;
import com.neu.csye6225.springdemo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUserInfo() {

        logger.info("Getting User info from db");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User createUser(User user) {

        logger.info("Creating a new User in db");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(UserRequest userRequest) {

        logger.info("Updating the User : "+userRequest.getUsername());
        User user = getUserInfo();
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setFirst_name(userRequest.getFirst_name());
        user.setLast_name(userRequest.getLast_name());
        user = userRepository.save(user);
        return user;
    }

    @Override
    public boolean isUsernameAlreadyExists(String username) {
        User user  = userRepository.findByUsername(username);
        return user!=null;
    }

}
