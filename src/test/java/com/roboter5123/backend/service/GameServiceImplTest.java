package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.JoinUpdate;
import com.roboter5123.backend.game.chat.ChatGame;
import com.roboter5123.backend.game.chat.ChatJoinUpdate;
import com.roboter5123.backend.messaging.MessageBroadcaster;
import com.roboter5123.backend.service.exception.TooManySessionsException;
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
    String sessioncode;

    Game chat;
    private String playerName;

    @BeforeEach
    void setup() {

        this.sessioncode = "abcd";
        this.playerName = "roboter5123";
        chat = Mockito.mock(ChatGame.class);

    }

    @Test
    void createSession() throws TooManySessionsException {

        when(sessionManager.createGameSession(GameMode.CHAT,gameService)).thenReturn(sessioncode);

        String result = gameService.createSession(GameMode.CHAT);
        assertEquals(sessioncode, result);
    }

    @Test
    void joinGame() {

        JoinUpdate chatJoinUpdate = new ChatJoinUpdate();
        when(sessionManager.getGameSession(sessioncode)).thenReturn(chat);
        when(chat.joinGame(playerName)).thenReturn(chatJoinUpdate);

        JoinPayloads expected = new JoinPayloads();
        expected.setBroadcast(chatJoinUpdate.getJoinCommand());
        expected.setReply(chatJoinUpdate.getGameState());

        JoinPayloads result = gameService.joinGame(sessioncode,playerName);
        assertEquals(expected.getBroadcast(), result.getBroadcast());
        assertEquals(expected.getReply(), result.getReply());
    }

    @Test
    void broadcastGameUpdate() {

        gameService.broadcastGameUpdate(sessioncode, sessioncode);
        verify(broadcaster).broadcastGameUpdate(sessioncode,sessioncode);
    }
}