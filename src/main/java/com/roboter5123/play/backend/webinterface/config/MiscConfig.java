package com.roboter5123.play.backend.webinterface.config;
import com.roboter5123.play.backend.game.api.GameFactory;
import com.roboter5123.play.backend.game.implementation.GameFactoryImpl;
import com.roboter5123.play.messaging.api.MessageBroadcaster;
import com.roboter5123.play.messaging.api.StompMessageFactory;
import com.roboter5123.play.messaging.implementation.MessageBroadcasterImpl;
import com.roboter5123.play.messaging.implementation.StompMessageFactoryImpl;
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
    MessageBroadcaster getMessageBroadcaster(StompMessageFactory messageFactory, SimpMessagingTemplate messagingTemplate){


        return new MessageBroadcasterImpl(messagingTemplate,messageFactory);
    }

    @Bean
    GameFactory getGameFactory(MessageBroadcaster broadcaster){

        return new GameFactoryImpl(broadcaster);
    }

}
