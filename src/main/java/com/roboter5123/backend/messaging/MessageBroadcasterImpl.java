package com.roboter5123.backend.messaging;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.messaging.model.StompMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageBroadcasterImpl implements MessageBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;
    private final StompMessageFactory factory;

    @Autowired
    public MessageBroadcasterImpl(final SimpMessagingTemplate messagingTemplate, final StompMessageFactory factory) {

        this.messagingTemplate = messagingTemplate;
        this.factory = factory;
    }

    public void broadcast(final String destination, final StompMessage message) {

        this.messagingTemplate.convertAndSend(destination, message);
    }

    @Override
    public void broadcastGameUpdate(final String sessionCode, final Object payload) {

        final String destination = "/topic/game/"+sessionCode;
        final StompMessage message = factory.getMessage(payload);
        broadcast(destination, message);
    }
}
