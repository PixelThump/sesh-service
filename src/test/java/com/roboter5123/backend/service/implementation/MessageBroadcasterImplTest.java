package com.roboter5123.backend.service.implementation;
import com.roboter5123.backend.game.api.MessageBroadcaster;
import com.roboter5123.backend.service.api.StompMessageFactory;
import com.roboter5123.backend.service.model.ErrorStompMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessageBroadcasterImplTest {

    @MockBean
    StompMessageFactory factory;
    @MockBean
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    MessageBroadcaster messageBroadcaster;
    String sessionCode;

    @BeforeEach
    void setUp() {

        sessionCode = "abcd";
    }

    @Test
    void broadcast_error_message_should_call_convert_and_send_with_correct_message() {

        RuntimeException exception = new RuntimeException("this is an error");

        ErrorStompMessage expected = new ErrorStompMessage();
        expected.setError(exception.getMessage());
        when(factory.getMessage(any(Object.class))).thenReturn(expected);

        messageBroadcaster.broadcastGameUpdate(sessionCode, exception);

        verify(messagingTemplate).convertAndSend("/topic/game/"+ sessionCode,expected);
    }
}