package com.roboter5123.play.backend.seshservice.messaging.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.seshservice.messaging.model.message.StompMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessageBroadcasterImpl implements MessageBroadcaster {

    private final SimpMessagingTemplate messagingTemplate;
    private final StompMessageFactory factory;
    private static final String ERROR_MESSAGE_LINE_2 = "No message type available in message factory for type {}";
    private static final String ERROR_MESSAGE_LINE_1 = "Could not broadcast message with payload {}";
    private static final String SESH_BASE_PATH = "/topic/sesh/";

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

        try {

            broadcastSeshUpdateToControllers(seshCode, payload);
            broadcastSeshUpdateToHost(seshCode, payload);

        } catch (UnsupportedOperationException e) {

            log.error(ERROR_MESSAGE_LINE_1, payload);
            log.error(ERROR_MESSAGE_LINE_2, payload.getClass());

            throw e;
        }
    }

    @Override
    public void broadcastSeshUpdateToControllers(String seshCode, Object payload) {

        final String destination = SESH_BASE_PATH + seshCode + "/controller";

        try {

            final StompMessage message = factory.getMessage(payload);
            broadcast(destination, message);

        } catch (UnsupportedOperationException e) {

            log.error(ERROR_MESSAGE_LINE_1, payload);
            log.error(ERROR_MESSAGE_LINE_2, payload.getClass());

            throw e;
        }
    }

    @Override
    public void broadcastSeshUpdateToHost(String seshcode, Object payload) {

        final String destination = SESH_BASE_PATH + seshcode + "/host";

        try {

            final StompMessage message = factory.getMessage(payload);
            broadcast(destination, message);

        } catch (UnsupportedOperationException e) {

            log.error(ERROR_MESSAGE_LINE_1, payload);
            log.error(ERROR_MESSAGE_LINE_2, payload.getClass());

            throw e;
        }
    }
}
