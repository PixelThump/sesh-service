package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.messaging.api.StompController;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.game.api.MessageBroadcaster;
import com.roboter5123.backend.service.api.StompMessageFactory;
import com.roboter5123.backend.service.model.ErrorStompMessage;
import com.roboter5123.backend.service.model.StateStompMessage;
import com.roboter5123.backend.service.model.StompMessage;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StompControllerImplTest {

    @MockBean
    GameService gameServiceMock;
    @MockBean
    MessageBroadcaster broadcasterMock;
    @MockBean
    StompMessageFactory factoryMock;
    @Autowired
    StompController stompController;

    String sessionCode;
    String playerName;

    @BeforeEach
    void setUp() {

        sessionCode = "abcd";
        playerName = "roboter5123";
    }

    @Test
    void joinSession_should_return_error_message_when_called_with_non_existent_session() {

        NoSuchSessionException exception = new NoSuchSessionException("No session with code " + sessionCode + " exists");
        when(gameServiceMock.joinGame(any(), any())).thenThrow(exception);

        ErrorStompMessage expected = new ErrorStompMessage(exception.getMessage());
        when(factoryMock.getMessage(exception)).thenReturn(expected);


        StompMessage result = stompController.joinSession(playerName,sessionCode);

        assertEquals(expected, result);
    }

    @Test
    void joinSession_should_return_state_message_when_called_with_existing_session() {

        Map<String, Object> state = new HashMap<>();
        state.put("a", new ArrayList<>());
        state.put("b", new ArrayList<>());
        when(gameServiceMock.joinGame(any(),any())).thenReturn(state);

        StompMessage expected = new StateStompMessage(state);
        when(factoryMock.getMessage(state)).thenReturn(expected);

        StompMessage result = stompController.joinSession(playerName,sessionCode);

        assertEquals(expected,result);
    }
}