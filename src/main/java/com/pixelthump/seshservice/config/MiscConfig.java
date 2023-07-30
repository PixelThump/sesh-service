package com.pixelthump.seshservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Configuration
public class MiscConfig {

    @Bean
    Random getSystemWideRandom() {

        return new Random();
    }

    @Bean
    RestTemplate getRestTemplate() {

        return new RestTemplate();
    }
}
