package com.roboter5123.backend.service.implementation;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.game.api.JoinUpdate;
import com.roboter5123.backend.game.implementation.chat.ChatGame;
import com.roboter5123.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.api.GameSessionManager;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import com.roboter5123.backend.service.model.JoinPayloads;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        when(sessionManager.createGameSession(GameMode.CHAT,gameService)).thenReturn(sessionCode);

        String result = gameService.createSession(GameMode.CHAT);
        assertEquals(sessionCode, result);
    }

    @Test
    void joinGame() {

        JoinUpdate chatJoinUpdate = new JoinUpdate();
        when(sessionManager.getGameSession(sessionCode)).thenReturn(chat);
        when(chat.joinGame(playerName)).thenReturn(chatJoinUpdate);

        JoinPayloads expected = new JoinPayloads();
        expected.setBroadcast(chatJoinUpdate.getCommand());
        expected.setReply(chatJoinUpdate.getGameState());

        JoinPayloads result = gameService.joinGame(sessionCode,playerName);
        assertEquals(expected.getBroadcast(), result.getBroadcast());
        assertEquals(expected.getReply(), result.getReply());
    }

    @Test
    void broadcastGameUpdate() {

        gameService.broadcastGameUpdate(sessionCode, sessionCode);
        verify(broadcaster).broadcastGameUpdate(sessionCode, sessionCode);
    }
}