package com.roboter5123.play.backend.webinterface.service.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameFactory;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.game.implementation.chat.ChatGame;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.webinterface.service.api.GameSessionManager;
import com.roboter5123.play.backend.webinterface.service.exception.NoSuchSessionException;
import com.roboter5123.play.backend.webinterface.service.exception.TooManySessionsException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameSessionManagerImplTest {

    @MockBean
    GameFactory factory;
    @Mock
    MessageBroadcaster broadcaster;

    @Autowired
    GameSessionManager sessionManager;
    @Mock
    Game chat;

    @BeforeEach
    void setUp() {

        chat = new ChatGame(broadcaster);
    }

    @Test
    @Order(1)
    void ALL_METHODS_SHOULD_CREATE_AND_RETURN_CHAT_GAME() throws TooManySessionsException {

        chat.setGameMode(GameMode.CHAT);
        when(factory.createGame(GameMode.CHAT)).thenReturn(chat);
        Game game = sessionManager.createGameSession(GameMode.CHAT);

        Game expected = chat;
        Game result = sessionManager.getGameSession(game.getSessionCode());
        assertEquals(expected, result);
    }

    @Test
    @Order(2)
    void GET_GAME_SESSION_SHOULD_THROW_EXCEPTION_WHEN_NO_SUCH_SESSION_EXISTS() throws TooManySessionsException {

        String sessionCode ="1234";
        assertThrows(NoSuchSessionException.class, ()->sessionManager.getGameSession(sessionCode));
    }

    @Test
    @Order(3)
    void CREATE_GAME_SESSION_SHOULD_THROW_EXCEPTION_WHEN_TOO_MANY_SESSIONS() throws TooManySessionsException {

        when(factory.createGame(any())).thenReturn(chat);

        double maxSessionCount = Math.pow(26,4)+1;
        assertThrows(TooManySessionsException.class, ()-> {

            for (int i = 0; i < maxSessionCount; i++) {

                sessionManager.createGameSession(GameMode.CHAT);
            }
        });
    }
}