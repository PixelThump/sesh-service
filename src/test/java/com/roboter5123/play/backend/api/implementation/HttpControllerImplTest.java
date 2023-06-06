package com.roboter5123.play.backend.api.implementation;
import com.roboter5123.play.backend.api.api.HttpController;
import com.roboter5123.play.backend.api.model.HttpGameDTO;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.game.implementation.chat.ChatGame;
import com.roboter5123.play.backend.service.api.GameService;
import com.roboter5123.play.backend.service.exception.TooManySessionsException;
import com.roboter5123.play.messaging.api.MessageBroadcaster;
import com.roboter5123.play.messaging.api.StompMessageFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void create_Session_should_return_http_game() throws TooManySessionsException {

        when(gameServiceMock.createSession(GameMode.CHAT)).thenReturn(Optional.of(game));

        HttpGameDTO expected = new HttpGameDTO(game.getGameMode(), game.getSessionCode());
        HttpGameDTO result = httpController.createSession(GameMode.CHAT);

        assertEquals(expected, result);
    }

    @Test
    void get_game_should_return_game() {

        when(gameServiceMock.getGame(any())).thenReturn(Optional.of(game));

        HttpGameDTO expected = new HttpGameDTO(GameMode.CHAT, sessionCode);
        HttpGameDTO result = httpController.getGame(sessionCode);

        assertEquals(expected, result);
    }
}