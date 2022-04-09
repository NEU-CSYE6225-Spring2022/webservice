package com.neu.csye6225.springdemo.service.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.neu.csye6225.springdemo.model.User;
import com.neu.csye6225.springdemo.repository.UserRepository;
import com.neu.csye6225.springdemo.request.UserRequest;
import com.neu.csye6225.springdemo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AmazonDynamoDB amazonDynamoDB;

    private AmazonSNS amazonSNS;

    @Value("${snsTopicArn}")
    private String snsTopicArn;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AmazonDynamoDB amazonDynamoDB
        , AmazonSNS amazonSNS) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.amazonDynamoDB = amazonDynamoDB;
        this.amazonSNS = amazonSNS;
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
        generateTokenAndMail(user);
        return user;
    }

    private void generateTokenAndMail(User user) {

        logger.info("generating token for the User with mail: " + user.getUsername());
        Random rand = new Random();
        long randomNo = rand.nextLong();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("userToken");
        long expiryTime = System.currentTimeMillis()/1000 + 5*60;
        Item item = new Item()
                .withPrimaryKey("email", user.getUsername())
                .withLong("token", randomNo)
                .withLong("ttl", expiryTime);
        table.putItem(item);
        pushToSNStopic(user, randomNo);
    }

    private void pushToSNStopic(User user, long randomNo) {

        logger.info("Pushing an Event to SNS topic for the User with mail: " + user.getUsername());
        PublishRequest publishRequest = new PublishRequest();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(user.getUsername());
        stringBuilder.append("-");
        stringBuilder.append(randomNo);
        publishRequest.setMessage(stringBuilder.toString());
        publishRequest.setTopicArn(snsTopicArn);
        amazonSNS.publish(publishRequest);
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

    @Override
    public void verifyUserEmail(String userEmail, long userToken) {

        logger.info("Verifying the userEmail:" + userEmail + " with Token:" + userToken);
        User user = userRepository.findByUsername(userEmail);
        if(user==null) {
            logger.info("User with username :"+ userEmail+" dosent exists in the System");
            return;
        }
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        Table table = dynamoDB.getTable("userToken");
        Item item = table.getItem("email", userEmail);
        if(item!=null) {
            long serverToken = item.getLong("token");
            if(serverToken == userToken) {
                logger.info("Verified the userEmail:" + userEmail + " with Token:" + userToken);
                user.setAccountVerified(true);
                userRepository.saveAndFlush(user);
            }
            logger.info("Token mismatched for userEmail:" + userEmail + " Given Token:" + userToken + " Expected Token:" + serverToken);
            return;
        }
        logger.info("No Token Exists in the DynamoDB for userEmail:" + userEmail);
    }

    @Override
    public boolean isUserVerified(String userName) {

        User user = userRepository.findByUsername(userName);
        if(user==null) {
            return false;
        }
        return user.isAccountVerified();
    }

}
