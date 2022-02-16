package com.neu.csye6225.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASIC_AUTH = "basicAuth";

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.neu.csye6225.springdemo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes()).securityContexts(List.of(securityContext()));

    }

    private List<SecurityScheme> securitySchemes() {
        return List.of(new BasicAuth(BASIC_AUTH));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(Arrays.asList(basicAuthReference())).forPaths(PathSelectors.any()).build();
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference(BASIC_AUTH, new AuthorizationScope[0]);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "My REST APIS", //title
                "Apis developed for the Course cyse6225.", //description
                "Version 1.0", //version
                "Terms of service", //terms of service URL
                new Contact("Manoj Reddy Amireddy", "https://amireddym.github.io/", "amireddy.m@northeastern.edu"),
                "License of API", "API license URL", Collections.emptyList()); // contact info
    }
}
