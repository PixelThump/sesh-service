package com.roboter5123.play.backend.service.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.messaging.model.BasicAction;
import com.roboter5123.play.backend.messaging.model.Command;
import com.roboter5123.play.backend.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.service.api.GameService;
import com.roboter5123.play.backend.service.api.GameSessionManager;
import com.roboter5123.play.backend.service.exception.NoSuchSessionException;
import com.roboter5123.play.backend.service.exception.TooManySessionsException;
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
class GameServiceImplTest {

    @MockBean
    MessageBroadcaster broadcaster;
    @MockBean
    GameSessionManager sessionManager;
    @Autowired
    GameService gameService;
    String sessionCode;
    Game game;
    private String playerName;

    @BeforeEach
    void setup() {

        this.sessionCode = "abcd";
        this.playerName = "roboter5123";
        game = Mockito.mock(Game.class);

    }

    @Test
    void create_session_should_return_game() throws TooManySessionsException {

        when(sessionManager.createGameSession(any())).thenReturn(game);

        Game expected = game;
        Game result = gameService.createSession(GameMode.UNKNOWN);
        assertEquals(expected, result);
    }

    @Test
    void create_session_should_throw_TooManySessionsException() {

        when(sessionManager.createGameSession(any())).thenThrow(new TooManySessionsException("Unable to create session because there were too many sessions"));
        assertThrows(TooManySessionsException.class, () -> gameService.createSession(GameMode.UNKNOWN));
    }

    @Test
    void get_game_should_return_optional_of_game() {

        when(sessionManager.getGameSession(any())).thenReturn(game);

        Game expected = game;
        Game result = gameService.getGame(sessionCode);
        assertEquals(expected, result);
    }

    @Test
    void get_game_should_return_empty_optional() {

        when(sessionManager.getGameSession(any())).thenThrow(new NoSuchSessionException("Could not join session with code " + sessionCode + ".Session not found."));
        assertThrows(NoSuchSessionException.class, () -> gameService.getGame(sessionCode));
    }

    @Test
    void joinGame_should_return_state() {

        Map<String, Object> expected = new HashMap<>();

        when(sessionManager.getGameSession(sessionCode)).thenReturn(game);
        when(game.joinGame(playerName)).thenReturn(expected);

        Map<String, Object> result = gameService.joinGame(sessionCode, playerName);
        assertEquals(expected, result);
    }

    @Test
    void joinGame_should_throw_no_such_session_exception() {

        when(sessionManager.getGameSession(any())).thenThrow(new NoSuchSessionException("Could not join session with code " + sessionCode + ".Session not found."));

        assertThrows(NoSuchSessionException.class, () -> gameService.joinGame(sessionCode, playerName));
    }

    @Test
    void sendCommandToGame_should_return_Acknowledment_Message() {

        when(sessionManager.getGameSession(sessionCode)).thenReturn(game);

        Command incomingCommand = new Command(playerName, new BasicAction(playerName, "Chat message"));
        CommandStompMessage incomingMessage = new CommandStompMessage(incomingCommand);

        gameService.sendCommandToGame(incomingMessage, sessionCode);

        verify(game).addCommand(incomingCommand);
    }

    @Test
    void sendCommandToGame_should_log_error_and_throw_no_such_session_exception() {

        NoSuchSessionException exception = new NoSuchSessionException("Could not join session with code " + sessionCode + ".Session not found.");
        when(sessionManager.getGameSession(any())).thenThrow(exception);

        Command incomingCommand = new Command(playerName, new BasicAction(playerName, "Chat message"));
        CommandStompMessage incomingMessage = new CommandStompMessage(incomingCommand);
        assertThrows(NoSuchSessionException.class, () -> gameService.sendCommandToGame(incomingMessage, sessionCode));
    }
}