package com.roboter5123.play.backend.seshservice.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.seshservice.messaging.implementation.MessageBroadcasterImpl;
import com.roboter5123.play.backend.seshservice.messaging.model.message.ErrorStompMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageBroadcasterImplTest {

    StompMessageFactory factory;
    SimpMessagingTemplate messagingTemplate;
    MessageBroadcaster messageBroadcaster;
    String sessionCode;


    @BeforeEach
    void setUp() {

        this.messagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
        this.factory = Mockito.mock(StompMessageFactory.class);
        this.messageBroadcaster = new MessageBroadcasterImpl(messagingTemplate, factory);
        sessionCode = "abcd";
    }

    @Test
    void broadcastSeshUpdate_should_call_convert_and_send_with_error_message() {

        RuntimeException exception = new RuntimeException("this is an error");

        ErrorStompMessage expected = new ErrorStompMessage();
        expected.setError(exception.getMessage());
        when(factory.getMessage(any(Object.class))).thenReturn(expected);

        messageBroadcaster.broadcastSeshUpdate(sessionCode, exception);

        verify(messagingTemplate).convertAndSend("/topic/sesh/" + sessionCode + "/controller", expected);
        verify(messagingTemplate).convertAndSend("/topic/sesh/" + sessionCode + "/host", expected);
    }

    @Test
    void broadcastSeshUpdate_WITH_NON_SUPPORTED_PAYLOAD_SHOULD_THROW_EXCEPTION() {

        when(factory.getMessage(factory)).thenThrow(new UnsupportedOperationException());
        assertThrows(UnsupportedOperationException.class, () -> messageBroadcaster.broadcastSeshUpdate(sessionCode, factory));
    }

    @Test
    void broadcastSeshUpdateToControllers_WITH_NON_SUPPORTED_PAYLOAD_SHOULD_THROW_EXCEPTION() {

        when(factory.getMessage(factory)).thenThrow(new UnsupportedOperationException());
        assertThrows(UnsupportedOperationException.class, () -> messageBroadcaster.broadcastSeshUpdateToControllers(sessionCode, factory));
    }

    @Test
    void broadcastSeshUpdateToHost_WITH_NON_SUPPORTED_PAYLOAD_SHOULD_THROW_EXCEPTION() {

        when(factory.getMessage(factory)).thenThrow(new UnsupportedOperationException());
        assertThrows(UnsupportedOperationException.class, () -> messageBroadcaster.broadcastSeshUpdateToHost(sessionCode, factory));
    }
}