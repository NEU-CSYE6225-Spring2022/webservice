package com.neu.csye6225.springdemo.config;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.neu.csye6225.springdemo.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SNSconfig {

    @Value("${spring.profiles.active:default}")
    private String profile;

    @Bean
    public AmazonSNS getSnsClient() {

        if(profile.equalsIgnoreCase(Constants.LOCAL)) {

            ProfileCredentialsProvider profileCredentialsProvider = new ProfileCredentialsProvider("dev");
            return AmazonSNSClientBuilder.standard().withRegion("us-east-1").withCredentials(profileCredentialsProvider).build();
        }
        if(profile.equalsIgnoreCase(Constants.DEV)){

            return AmazonSNSClientBuilder.standard().build();
        }

        EnvironmentVariableCredentialsProvider environmentVariableCredentialsProvider = new EnvironmentVariableCredentialsProvider();
        return AmazonSNSClientBuilder.standard().withRegion("us-east-1").withCredentials(environmentVariableCredentialsProvider).build();
    }
}
