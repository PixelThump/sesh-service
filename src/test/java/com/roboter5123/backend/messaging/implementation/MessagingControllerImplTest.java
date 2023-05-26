package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.game.api.Command;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.game.implementation.chat.ChatMessageAction;
import com.roboter5123.backend.game.implementation.chat.ChatState;
import com.roboter5123.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.backend.messaging.api.MessagingController;
import com.roboter5123.backend.messaging.api.StompMessageFactory;
import com.roboter5123.backend.messaging.model.ErrorStompMessage;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.model.JoinPayloads;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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

    String sessionCode;
    String playerName;

    @BeforeEach
    void setUp() {

        sessionCode = "abcd";
        playerName = "roboter5123";
    }

    @Test
    void createSession_should_return_session_code() throws TooManySessionsException {

        when(gameServiceMock.createSession(GameMode.CHAT)).thenReturn(sessionCode);

        String expected = sessionCode;
        String result = messagingController.createSession(GameMode.CHAT);

        assertEquals(expected, result);
    }

    @Test
    void joinSession_should_call_broadcaster_with_join_message_and_return_current_state() {

        Command broadcast = new Command("server", new ChatMessageAction());
        ChatState reply = new ChatState();
        JoinPayloads joinPayloads = new JoinPayloads();
        joinPayloads.setReply(reply.getState());
        joinPayloads.setBroadcast(broadcast);

        when(gameServiceMock.joinGame(eq(sessionCode), any())).thenReturn(joinPayloads);

        StompMessage expected = factoryMock.getMessage(reply);
        StompMessage result = messagingController.joinSession("ab", sessionCode);

        Mockito.verify(broadcasterMock).broadcastGameUpdate(sessionCode, broadcast);
        assertEquals(expected, result);
    }

    @Test
    void joinSession_should_return_error_message_when_called_with_non_existent_session() {

        NoSuchSessionException exception = new NoSuchSessionException("No session with code " + sessionCode + " exists");
        when(gameServiceMock.joinGame(any(), any())).thenThrow(exception);

        ErrorStompMessage expected = new ErrorStompMessage(exception.getMessage());
        when(factoryMock.getMessage(exception)).thenReturn(expected);


        StompMessage result = messagingController.joinSession(playerName,sessionCode);

        assertEquals(expected, result);
    }

    @Test
    void broadcast_should_call_broadcaster(){

        messagingController.broadcast(sessionCode, playerName);
        verify(broadcasterMock).broadcastGameUpdate(any(),any());
    }
}