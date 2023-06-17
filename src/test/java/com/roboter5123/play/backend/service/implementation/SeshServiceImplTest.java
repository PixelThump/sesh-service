package com.roboter5123.play.backend.service.implementation;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.BasicAction;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.service.api.SeshService;
import com.roboter5123.play.backend.seshservice.service.api.SeshManager;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SeshServiceImplTest {

    @MockBean
    MessageBroadcaster broadcaster;
    @MockBean
    SeshManager sessionManager;
    @Autowired
    SeshService seshService;
    String sessionCode;
    Sesh sesh;
    private String playerName;

    @BeforeEach
    void setup() {

        this.sessionCode = "abcd";
        this.playerName = "roboter5123";
        sesh = Mockito.mock(Sesh.class);

    }

    @Test
    void create_session_should_return_game() throws TooManySeshsException {

        when(sessionManager.createSesh(any())).thenReturn(sesh);

        Sesh expected = sesh;
        Sesh result = seshService.createSesh(SeshType.UNKNOWN);
        assertEquals(expected, result);
    }

    @Test
    void create_session_should_throw_TooManySessionsException() {

        when(sessionManager.createSesh(any())).thenThrow(new TooManySeshsException("Unable to create session because there were too many sessions"));
        assertThrows(TooManySeshsException.class, () -> seshService.createSesh(SeshType.UNKNOWN));
    }

    @Test
    void get_game_should_return_optional_of_game() {

        when(sessionManager.getSesh(any())).thenReturn(sesh);

        Sesh expected = sesh;
        Sesh result = seshService.getSesh(sessionCode);
        assertEquals(expected, result);
    }

    @Test
    void get_game_should_return_empty_optional() {

        when(sessionManager.getSesh(any())).thenThrow(new NoSuchSeshException("Could not join session with code " + sessionCode + ".Session not found."));
        assertThrows(NoSuchSeshException.class, () -> seshService.getSesh(sessionCode));
    }

    @Test
    void joinGame_should_return_state() {

        Map<String, Object> expected = new HashMap<>();

        when(sessionManager.getSesh(sessionCode)).thenReturn(sesh);
        when(sesh.joinSesh(playerName)).thenReturn(expected);

        Map<String, Object> result = seshService.joinSesh(sessionCode, playerName);
        assertEquals(expected, result);
    }

    @Test
    void joinGame_should_throw_no_such_session_exception() {

        when(sessionManager.getSesh(any())).thenThrow(new NoSuchSeshException("Could not join session with code " + sessionCode + ".Session not found."));

        assertThrows(NoSuchSeshException.class, () -> seshService.joinSesh(sessionCode, playerName));
    }

    @Test
    void sendCommandToGame_should_return_Acknowledment_Message() {

        when(sessionManager.getSesh(sessionCode)).thenReturn(sesh);

        Command incomingCommand = new Command(playerName, new BasicAction(playerName, "Chat message"));
        CommandStompMessage incomingMessage = new CommandStompMessage(incomingCommand);

        seshService.sendCommandToSesh(incomingMessage, sessionCode);

        verify(sesh).addCommand(incomingCommand);
    }

    @Test
    void sendCommandToGame_should_log_error_and_throw_no_such_session_exception() {

        NoSuchSeshException exception = new NoSuchSeshException("Could not join session with code " + sessionCode + ".Session not found.");
        when(sessionManager.getSesh(any())).thenThrow(exception);

        Command incomingCommand = new Command(playerName, new BasicAction(playerName, "Chat message"));
        CommandStompMessage incomingMessage = new CommandStompMessage(incomingCommand);
        assertThrows(NoSuchSeshException.class, () -> seshService.sendCommandToSesh(incomingMessage, sessionCode));
    }
}