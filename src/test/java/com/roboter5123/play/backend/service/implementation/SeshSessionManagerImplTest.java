package com.roboter5123.play.backend.service.implementation;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshFactory;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.sesh.implementation.chat.ChatSesh;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.service.api.SeshManager;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
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
class SeshSessionManagerImplTest {

    @MockBean
    SeshFactory factory;
    @Mock
    MessageBroadcaster broadcaster;

    @Autowired
    SeshManager sessionManager;
    @Mock
    Sesh chat;

    @BeforeEach
    void setUp() {

        chat = new ChatSesh(broadcaster);
    }

    @Test
    @Order(1)
    void ALL_METHODS_SHOULD_CREATE_AND_RETURN_CHAT_GAME() throws TooManySeshsException {

        chat.setSeshType(SeshType.CHAT);
        when(factory.createSesh(SeshType.CHAT)).thenReturn(chat);
        Sesh sesh = sessionManager.createSesh(SeshType.CHAT);

        Sesh expected = chat;
        Sesh result = sessionManager.getSesh(sesh.getSeshCode());
        assertEquals(expected, result);
    }

    @Test
    @Order(2)
    void GET_GAME_SESSION_SHOULD_THROW_EXCEPTION_WHEN_NO_SUCH_SESSION_EXISTS() throws TooManySeshsException {

        String sessionCode ="1234";
        assertThrows(NoSuchSeshException.class, ()->sessionManager.getSesh(sessionCode));
    }

    @Test
    @Order(3)
    void CREATE_GAME_SESSION_SHOULD_THROW_EXCEPTION_WHEN_TOO_MANY_SESSIONS() throws TooManySeshsException {

        when(factory.createSesh(any())).thenReturn(chat);

        double maxSessionCount = Math.pow(26,4)+1;
        assertThrows(TooManySeshsException.class, ()-> {

            for (int i = 0; i < maxSessionCount; i++) {

                sessionManager.createSesh(SeshType.CHAT);
            }
        });
    }
}