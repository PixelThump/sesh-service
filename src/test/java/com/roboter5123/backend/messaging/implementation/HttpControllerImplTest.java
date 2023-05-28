package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.game.implementation.chat.ChatGame;
import com.roboter5123.backend.messaging.api.HttpController;
import com.roboter5123.backend.messaging.model.HttpGame;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.api.MessageBroadcaster;
import com.roboter5123.backend.service.api.StompMessageFactory;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    String playerName;

    @BeforeEach
    void setUp() {

        sessionCode = "abcd";
        playerName = "roboter5123";
    }

    @Test
    void create_Session_should_return_session_code() throws TooManySessionsException {

        when(gameServiceMock.createSession(GameMode.CHAT)).thenReturn(sessionCode);

        String expected = sessionCode;
        String result = httpController.createSession(GameMode.CHAT);

        assertEquals(expected, result);
    }

    @Test
    void get_game_should_return_game(){

        Game game = new ChatGame();
        game.setGameMode(GameMode.CHAT);
        game.setSessionCode(sessionCode);
        when(gameServiceMock.getGame(any())).thenReturn(Optional.of(game));

        HttpGame expected = new HttpGame(GameMode.CHAT,sessionCode);
        HttpGame result = httpController.getGame(sessionCode);

        assertEquals(expected, result);
    }
}