package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.chat.ChatGame;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.exception.TooManySessionsException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameSessionManagerImplTest {

    @MockBean
    GameFactory factory;
    @Mock
    GameService service;

    @Autowired
    GameSessionManager sessionManager;
    @Mock
    Game chat;

    @BeforeEach
    void setUp() {

        chat = new ChatGame();
    }

    @Test
    @Order(1)
    void ALL_METHODS_SHOULD_CREATE_AND_RETURN_CHAT_GAME() throws TooManySessionsException {

        when(factory.createGame(eq(GameMode.CHAT), any())).thenReturn(chat);
        String sessionCode = sessionManager.createGameSession(GameMode.CHAT, service);

        Game expected = chat;
        Game result = sessionManager.getGameSession(sessionCode);
        assertEquals(expected, result);
    }

    @Test
    @Order(2)
    void GETGAMESESSION_SHOULD_THROW_EXCEPTION_WHEN_NO_SUCH_SESSION_EXISTS() throws TooManySessionsException {

        String sessionCode ="1234";
        assertThrows(NoSuchSessionException.class, ()->sessionManager.getGameSession(sessionCode));
    }

    @Test
    @Order(3)
    void CREATEGAMESESSION_SHOULD_THROW_EXCEPTION_WHEN_TOO_MANY_SESSIONS() throws TooManySessionsException {


        double maxSessionCount = Math.pow(26,4)+1;
        assertThrows(TooManySessionsException.class, ()-> {

            for (int i = 0; i < maxSessionCount; i++) {

                sessionManager.createGameSession(GameMode.CHAT,service);
            }
        });
    }

}