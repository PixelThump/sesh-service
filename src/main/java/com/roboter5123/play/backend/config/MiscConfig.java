package com.roboter5123.play.backend.config;
import com.roboter5123.play.messaging.api.MessageBroadcaster;
import com.roboter5123.play.messaging.api.StompMessageFactory;
import com.roboter5123.play.messaging.implementation.MessageBroadcasterImpl;
import com.roboter5123.play.messaging.implementation.StompMessageFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Random;

@Configuration
public class MiscConfig {

    @Bean
    Random getSystemWideRandom(){

        return new Random();
    }

    @Bean
    StompMessageFactory getMessagingFactory(){

        return new StompMessageFactoryImpl();
    }
    @Bean
    @Autowired
    MessageBroadcaster getMessageBroadcaster(StompMessageFactory messageFactory, SimpMessagingTemplate messagingTemplate){


        return new MessageBroadcasterImpl(messagingTemplate,messageFactory);
    }

}
