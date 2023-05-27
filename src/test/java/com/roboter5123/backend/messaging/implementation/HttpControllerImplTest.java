package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.messaging.api.HttpController;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.api.MessageBroadcaster;
import com.roboter5123.backend.service.api.StompMessageFactory;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
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
    void createSession_should_return_session_code() throws TooManySessionsException {

        when(gameServiceMock.createSession(GameMode.CHAT)).thenReturn(sessionCode);

        String expected = sessionCode;
        String result = httpController.createSession(GameMode.CHAT);

        assertEquals(expected, result);
    }
}