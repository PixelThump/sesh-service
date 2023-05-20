package com.roboter5123.backend.messaging;
import com.roboter5123.backend.messaging.model.StompMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageBroadcasterImpl implements MessageBroadcaster {

    SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageBroadcasterImpl(SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void broadcast(String destination, StompMessage message) {

        this.messagingTemplate.convertAndSend(destination, message);
    }
}
