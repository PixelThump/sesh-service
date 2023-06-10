package com.roboter5123.play.backend.webinterface.service.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.game.implementation.chat.ChatGame;
import com.roboter5123.play.backend.webinterface.service.api.GameService;
import com.roboter5123.play.backend.webinterface.service.api.GameSessionManager;
import com.roboter5123.play.backend.webinterface.service.exception.TooManySessionsException;
import com.roboter5123.play.messaging.api.MessageBroadcaster;
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

    Game chat;
    private String playerName;

    @BeforeEach
    void setup() {

        this.sessionCode = "abcd";
        this.playerName = "roboter5123";
        chat = Mockito.mock(ChatGame.class);

    }

    @Test
    void createSession() throws TooManySessionsException {

        when(sessionManager.createGameSession(GameMode.CHAT)).thenReturn(chat);

        Optional<Game> expected = Optional.of(chat);
        Optional<Game> result = gameService.createSession(GameMode.CHAT);
        assertEquals(expected, result);
    }

    @Test
    void joinGame() {

        Map<String, Object> expected = new HashMap<>();

        when(sessionManager.getGameSession(sessionCode)).thenReturn(chat);
        when(chat.joinGame(playerName)).thenReturn(expected);

        Map<String,Object> result = gameService.joinGame(sessionCode,playerName);
        assertEquals(expected, result);
    }
}