package com.roboter5123.play.backend.webinterface.service.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.webinterface.service.api.GameService;
import com.roboter5123.play.backend.webinterface.service.api.GameSessionManager;
import com.roboter5123.play.backend.webinterface.service.exception.NoSuchSessionException;
import com.roboter5123.play.backend.webinterface.service.exception.TooManySessionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

        Optional<Game> expected = Optional.of(game);
        Optional<Game> result = gameService.createSession(GameMode.UNKNOWN);
        assertEquals(expected, result);
    }

    @Test
    void create_session_should_return_empty_optional() throws TooManySessionsException {

        when(sessionManager.createGameSession(any())).thenThrow(new TooManySessionsException());

        Optional<Game> expected = Optional.empty();
        Optional<Game> result = gameService.createSession(GameMode.UNKNOWN);
        assertEquals(expected, result);
    }

    @Test
    void get_game_should_return_optional_of_game() throws TooManySessionsException {

        when(sessionManager.getGameSession(any())).thenReturn(game);

        Optional<Game> expected = Optional.of(game);
        Optional<Game> result = gameService.getGame(sessionCode);
        assertEquals(expected, result);
    }

    @Test
    void get_game_should_return_empty_optional() throws TooManySessionsException {

        when(sessionManager.getGameSession(any())).thenThrow(new NoSuchSessionException("So Session with code " + sessionCode + "exists."));

        Optional<Game> expected = Optional.empty();
        Optional<Game> result = gameService.getGame(sessionCode);

        assertEquals(expected, result);
    }

    @Test
    void joinGame_should_return_state() {

        Map<String, Object> expected = new HashMap<>();

        when(sessionManager.getGameSession(sessionCode)).thenReturn(game);
        when(game.joinGame(playerName)).thenReturn(expected);

        Map<String,Object> result = gameService.joinGame(sessionCode,playerName);
        assertEquals(expected, result);
    }

    @Test
    void joinGame_should_throw_no_such_session_exception() {

        when(sessionManager.getGameSession(any())).thenThrow(new NoSuchSessionException("So Session with code " + sessionCode + "exists."));

        assertThrows(NoSuchSessionException.class, ()-> gameService.joinGame(sessionCode,playerName));
    }
}