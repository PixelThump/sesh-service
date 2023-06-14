package com.roboter5123.play.backend.webinterface.api.implementation;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.messaging.model.*;
import com.roboter5123.play.backend.webinterface.api.api.StompController;
import com.roboter5123.play.backend.webinterface.service.api.GameService;
import com.roboter5123.play.backend.webinterface.service.api.GameSessionManager;
import com.roboter5123.play.backend.webinterface.service.exception.NoSuchSessionException;
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
import static org.mockito.ArgumentMatchers.eq;
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
    @MockBean
    GameSessionManager gameSessionManager;

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

    @Test
    void sendCommandToGame_Should_Return_Acknowledgement_Message(){

        when(gameServiceMock.sendCommandToGame(any(),eq(sessionCode))).thenReturn( new GenericStompMessage());

        StompMessage expected = new GenericStompMessage();

        Command incomingCommand = new Command(playerName,new BasicAction(playerName,"Chat message"));
        CommandStompMessage incomingMessage = new CommandStompMessage(incomingCommand);
        StompMessage result = stompController.sendCommandToGame(incomingMessage, sessionCode);
        assertEquals(expected, result);
    }

    @Test
    void sendCommandToGame_Should_Return_ErrorMessage(){

        NoSuchSessionException exception = new NoSuchSessionException("No Session with code " + sessionCode + " exists");
        when(gameServiceMock.sendCommandToGame(any(),eq(sessionCode))).thenThrow(exception);
        when(factoryMock.getMessage(exception)).thenReturn(new ErrorStompMessage(exception.getMessage()));

        StompMessage expected = new ErrorStompMessage(exception.getMessage());

        Command incomingCommand = new Command(playerName, new BasicAction(playerName,"Chat message"));
        CommandStompMessage incomingMessage = new CommandStompMessage(incomingCommand);
        StompMessage result = stompController.sendCommandToGame(incomingMessage, sessionCode);
        assertEquals(expected, result);
    }
}