package com.roboter5123.play.backend.messaging.implementation;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.messaging.model.ErrorStompMessage;
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
    void broadcast_error_message_should_call_convert_and_send_with_correct_message() {

        RuntimeException exception = new RuntimeException("this is an error");

        ErrorStompMessage expected = new ErrorStompMessage();
        expected.setError(exception.getMessage());
        when(factory.getMessage(any(Object.class))).thenReturn(expected);

        messageBroadcaster.broadcastGameUpdate(sessionCode, exception);

        verify(messagingTemplate).convertAndSend("/topic/game/" + sessionCode, expected);
    }

    @Test
    void BROADCAST_WITH_NON_SUPPORTED_PAYLOAD_SHOULD_THROW_EXCEPTION() {

        when(factory.getMessage(factory)).thenThrow(new UnsupportedOperationException());
        assertThrows(UnsupportedOperationException.class, () -> messageBroadcaster.broadcastGameUpdate(sessionCode, factory));
    }
}