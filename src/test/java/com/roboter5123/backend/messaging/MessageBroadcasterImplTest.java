package com.roboter5123.backend.messaging;
import com.roboter5123.backend.messaging.model.ErrorStompMessage;
import com.roboter5123.backend.messaging.model.StompMessageFactory;
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
    String sessioncode;

    @BeforeEach
    void setUp() {

        sessioncode = "abcd";
    }

    @Test
    void broadcasterrormessage_should_call_convertandsend_with_correct_message() {

        RuntimeException exception = new RuntimeException("this is an error");

        ErrorStompMessage expected = new ErrorStompMessage();
        expected.setError(exception.getMessage());
        when(factory.getMessage(any(Object.class))).thenReturn(expected);

        messageBroadcaster.broadcastGameUpdate(sessioncode, exception);

        verify(messagingTemplate).convertAndSend("/topic/game/"+sessioncode,expected);
    }
}