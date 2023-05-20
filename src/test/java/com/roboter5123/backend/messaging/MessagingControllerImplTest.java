package com.roboter5123.backend.messaging;
import com.roboter5123.backend.game.chat.ChatMessageAction;
import com.roboter5123.backend.game.chat.ChatMessageCommand;
import com.roboter5123.backend.game.chat.ChatState;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.messaging.model.StompMessageFactory;
import com.roboter5123.backend.service.GameService;
import com.roboter5123.backend.service.model.JoinPayloads;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessagingControllerImplTest {

    @MockBean
    GameService gameServiceMock;

    @MockBean
    MessageBroadcaster broadcasterMock;

    @MockBean
    StompMessageFactory factoryMock;

    @Autowired
    MessagingController messagingController;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createSession_should_return_session_code() {

    }

    @Test
    void joinSession_should_call_broadcaster_with_join_message_and_return_current_state() {

        ChatMessageCommand broadcast = new ChatMessageCommand("server", new ChatMessageAction());
        ChatState reply = new ChatState();
        JoinPayloads joinPayloads = new JoinPayloads();
        joinPayloads.setReply(reply);
        joinPayloads.setBroadcast(broadcast);
        String sessionCode = "abcd";
        when(gameServiceMock.joinGame(eq(sessionCode),any())).thenReturn(joinPayloads);


        StompMessage expected = factoryMock.getMessage(reply);
        StompMessage result = messagingController.joinSession(sessionCode,"abc");

        Mockito.verify(broadcasterMock).broadcastGameUpdate(sessionCode,broadcast);
        assertEquals(expected, result);
    }

    @Test
    void joinSession_should_return_error_message_when_called_with_non_existent_session() {


    }

    @Test
    void broadcast() {

    }
}