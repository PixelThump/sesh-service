package com.roboter5123.play.backend.webinterface.api.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.game.implementation.chat.ChatGame;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.webinterface.api.api.HttpController;
import com.roboter5123.play.backend.webinterface.api.model.HttpGameDTO;
import com.roboter5123.play.backend.webinterface.api.model.exception.NoSuchSessionHttpException;
import com.roboter5123.play.backend.webinterface.api.model.exception.TooManySessionsHttpException;
import com.roboter5123.play.backend.webinterface.service.api.GameService;
import com.roboter5123.play.backend.webinterface.service.api.GameSessionManager;
import com.roboter5123.play.backend.webinterface.service.exception.TooManySessionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class HttpControllerImplTest {

    @MockBean
    GameService gameServiceMock;
    @MockBean
    MessageBroadcaster broadcasterMock;
    @MockBean
    StompMessageFactory factoryMock;

    @MockBean
    GameSessionManager gameSessionManager;
    @Autowired
    HttpController httpController;
    String sessionCode;
    Game game;

    @BeforeEach
    void setUp() {

        sessionCode = "abcd";
        this.game = new ChatGame(broadcasterMock);
        this.game.setGameMode(GameMode.CHAT);
        this.game.setSessionCode(sessionCode);
    }

    @Test
    void get_Game_Modes_should_return_all_game_modes_but_unknown(){

        List<GameMode> expected = Arrays.stream(GameMode.values()).filter(gameMode -> gameMode!=GameMode.UNKNOWN).toList();
        List<GameMode> result = httpController.getGameModes();
        assertEquals(expected, result);
    }

    @Test
    void create_Session_should_return_http_game() throws TooManySessionsException {

        when(gameServiceMock.createSession(GameMode.CHAT)).thenReturn(Optional.of(game));

        HttpGameDTO expected = new HttpGameDTO(game.getGameMode(), game.getSessionCode());
        HttpGameDTO result = httpController.createSession(GameMode.CHAT);

        assertEquals(expected, result);
    }

    @Test
    void create_Session_with_too_many_sessions_should_throw_too_many_sessions_exception() throws TooManySessionsException{

        when(gameServiceMock.createSession(any())).thenReturn(Optional.empty());

        assertThrows(TooManySessionsHttpException.class, ()-> httpController.createSession(GameMode.UNKNOWN));
    }

    @Test
    void get_game_should_return_game() {

        when(gameServiceMock.getGame(any())).thenReturn(Optional.of(game));

        HttpGameDTO expected = new HttpGameDTO(GameMode.CHAT, sessionCode);
        HttpGameDTO result = httpController.getGame(sessionCode);

        assertEquals(expected, result);
    }

    @Test
    void get_game_should_throw_so_such_session_exception() {

        when(gameServiceMock.getGame(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchSessionHttpException.class, ()-> httpController.getGame(sessionCode));
    }
}