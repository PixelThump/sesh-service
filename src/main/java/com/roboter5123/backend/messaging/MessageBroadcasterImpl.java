package com.roboter5123.backend.messaging;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.messaging.model.StompMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageBroadcasterImpl implements MessageBroadcaster {

    SimpMessagingTemplate messagingTemplate;
    StompMessageFactory factory;

    @Autowired
    public MessageBroadcasterImpl(SimpMessagingTemplate messagingTemplate, StompMessageFactory factory) {

        this.messagingTemplate = messagingTemplate;
        this.factory = factory;
    }

    @Override
    public void broadcast(String destination, StompMessage message) {

        this.messagingTemplate.convertAndSend(destination, message);
    }

    @Override
    public void broadcastGameUpdate(String sessionCode, Object payload) {

        String destination = "/topic/game/"+sessionCode;
        StompMessage message = factory.getMessage(payload);
        broadcast(destination, message);
    }
}
