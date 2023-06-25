package com.roboter5123.play.backend.seshservice.messaging.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.seshservice.messaging.model.StompMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
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
    public void broadcastSeshUpdate(final String seshCode, final Object payload) throws UnsupportedOperationException {

        final String destination = "/topic/sesh/" + seshCode;

        try {

            final StompMessage message = factory.getMessage(payload);
            broadcast(destination, message);

        } catch (UnsupportedOperationException e) {

            log.error("Could not broadcast message with payload {}", payload);
            log.error("No message type available in message factory for type {}", payload.getClass());

            throw e;
        }
    }
}
