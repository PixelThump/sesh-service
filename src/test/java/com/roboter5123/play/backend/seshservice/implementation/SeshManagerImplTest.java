package com.roboter5123.play.backend.seshservice.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.service.api.SeshManager;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshFactory;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.sesh.implementation.chat.ChatSesh;
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
class SeshManagerImplTest {

    @MockBean
    SeshFactory factory;
    @Mock
    MessageBroadcaster broadcaster;

    @Autowired
    SeshManager seshManager;
    @Mock
    Sesh chat;

    @BeforeEach
    void setUp() {

        chat = new ChatSesh(broadcaster);
    }
    @AfterEach
    void cleanup(){

        this.seshManager.clearSeshs();
    }

    @Test
    void create_sesh_and_get_sesh_should_create_and_return_chat_game() throws TooManySeshsException {

        chat.setSeshType(SeshType.CHAT);
        when(factory.createSesh(SeshType.CHAT)).thenReturn(chat);
        Sesh sesh = seshManager.createSesh(SeshType.CHAT);

        Sesh expected = chat;
        Sesh result = seshManager.getSesh(sesh.getSeshCode());
        assertEquals(expected, result);
    }

    @Test
    void GET_GAME_SESSION_SHOULD_THROW_EXCEPTION_WHEN_NO_SUCH_SESSION_EXISTS() throws TooManySeshsException {

        String sessionCode ="1234";
        assertThrows(NoSuchSeshException.class, ()-> seshManager.getSesh(sessionCode));
    }

    @Test
    void CREATE_GAME_SESSION_SHOULD_THROW_EXCEPTION_WHEN_TOO_MANY_SESSIONS() throws TooManySeshsException {

        when(factory.createSesh(any())).thenReturn(chat);

        double maxSessionCount = Math.pow(26,4)+1;
        assertThrows(TooManySeshsException.class, ()-> {

            for (int i = 0; i < maxSessionCount; i++) {

                seshManager.createSesh(SeshType.CHAT);
            }
        });
    }
}