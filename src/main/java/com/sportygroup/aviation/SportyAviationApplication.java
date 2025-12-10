package com.sportygroup.aviation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class SportyAviationApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportyAviationApplication.class, args);
    }
}
