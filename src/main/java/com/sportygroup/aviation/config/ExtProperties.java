package com.sportygroup.aviation.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ExtProperties {

    @Value("${spring.application.name}")
    private String appName;
    @Value("${sporty.aviation.basic.auth.username}")
    private String basicAuthUserName;
    @Value("${sporty.aviation.basic.auth.password}")
    private String basicAuthPassword;

    @Value("${sporty.aviation.ext.auth.username}")
    private String extAuthUserName;
    @Value("${sporty.aviation.ext.auth.password}")
    private String extAuthPassword;
    @Value("${sporty.aviation.base.url}")
    private String extBaseUrl;
    @Value("${sporty.aviation.airport.details}")
    private String extAirportDetailsEndPoint;
}
