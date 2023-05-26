package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.backend.messaging.api.StompMessageFactory;
import com.roboter5123.backend.messaging.model.StompMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageBroadcasterImpl implements MessageBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;
    private final StompMessageFactory factory;

    private final Logger logger;

    @Autowired
    public MessageBroadcasterImpl(final SimpMessagingTemplate messagingTemplate, final StompMessageFactory factory) {

        this.messagingTemplate = messagingTemplate;
        this.factory = factory;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public void broadcast(final String destination, final StompMessage message) {

        this.messagingTemplate.convertAndSend(destination, message);
    }

    @Override
    public void broadcastGameUpdate(final String sessionCode, final Object payload) throws UnsupportedOperationException {

        final String destination = "/topic/game/"+sessionCode;

        try {

            final StompMessage message = factory.getMessage(payload);
            broadcast(destination, message);
        }catch (UnsupportedOperationException e){

            logger.error("Could not broadcast message with payload {}", payload);
            logger.error("No message type available in message factory for type {}", payload.getClass());
            throw e;
        }


    }
}
