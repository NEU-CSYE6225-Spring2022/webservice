package com.neu.csye6225.springdemo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.neu.csye6225.springdemo.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Value("spring.profiles.active")
    private String profile;

    @Bean
    public AmazonS3 s3() {

//        AWSCredentials awsCredentials =
//                new BasicAWSCredentials("accessKey", "secretKey");

//                return AmazonS3ClientBuilder
//                .standard()
//                .withRegion("ap-south-1")
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .build();
        if(profile.equalsIgnoreCase(Constants.LOCAL)){
            ProfileCredentialsProvider profileCredentialsProvider = new ProfileCredentialsProvider("dev");
            return AmazonS3ClientBuilder.standard().withRegion("us-east-1").withCredentials(profileCredentialsProvider).build();
        }
        if(profile.equalsIgnoreCase(Constants.DEV)){
            return AmazonS3ClientBuilder.standard().build();
        }

        EnvironmentVariableCredentialsProvider environmentVariableCredentialsProvider = new EnvironmentVariableCredentialsProvider();
        return AmazonS3ClientBuilder.standard().withRegion("us-east-1").withCredentials(environmentVariableCredentialsProvider).build();

    }

}
