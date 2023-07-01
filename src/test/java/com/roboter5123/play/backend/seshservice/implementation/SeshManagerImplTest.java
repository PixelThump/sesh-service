package com.roboter5123.play.backend.seshservice.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.service.api.SeshManager;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshFactory;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelSesh;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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
    Sesh quizxel;
    String seshCode;

    @BeforeEach
    void setUp() {

        this.seshCode = "ABCD";
        quizxel = new QuizxelSesh(seshCode, broadcaster);
    }

    @AfterEach
    void cleanup() {

        this.seshManager.clearSeshs();
    }

    @Test
    void create_sesh_and_get_sesh_should_create_and_return_chat_game() throws TooManySeshsException {

        when(factory.createSesh(any(), eq(SeshType.QUIZXEL))).thenReturn(quizxel);
        ArgumentCaptor<String> capturedArgument = ArgumentCaptor.forClass(String.class);
        Sesh expected = seshManager.createSesh(SeshType.QUIZXEL);
        verify(factory).createSesh(capturedArgument.capture(), eq(SeshType.QUIZXEL));
        Sesh result = seshManager.getSesh(capturedArgument.getValue());

        assertEquals(expected, result);
    }

    @Test
    void GET_GAME_SESSION_SHOULD_THROW_EXCEPTION_WHEN_NO_SUCH_SESSION_EXISTS() throws TooManySeshsException {

        String sessionCode = "1234";
        assertThrows(NoSuchSeshException.class, () -> seshManager.getSesh(sessionCode));
    }

    @Test
    void CREATE_GAME_SESSION_SHOULD_THROW_EXCEPTION_WHEN_TOO_MANY_SESSIONS() throws TooManySeshsException {

        when(factory.createSesh(any(), any())).thenReturn(quizxel);

        double maxSessionCount = Math.pow(26, 4) + 1;
        assertThrows(TooManySeshsException.class, () -> {

            for (int i = 0; i < maxSessionCount; i++) {

                seshManager.createSesh(SeshType.QUIZXEL);
            }
        });
    }
}