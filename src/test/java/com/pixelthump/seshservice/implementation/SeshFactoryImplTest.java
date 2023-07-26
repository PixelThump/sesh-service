package com.pixelthump.seshservice.implementation;
import com.pixelthump.seshservice.messaging.api.MessageBroadcaster;
import com.pixelthump.seshservice.sesh.api.Sesh;
import com.pixelthump.seshservice.sesh.api.SeshFactory;
import com.pixelthump.seshservice.sesh.implementation.SeshFactoryImpl;
import com.pixelthump.seshservice.sesh.implementation.quizxel.QuizxelSesh;
import com.pixelthump.seshservice.sesh.model.SeshType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SeshFactoryImplTest {

    SeshFactory seshfactory;
    String seshCode;
    @MockBean
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {

        MessageBroadcaster broadcaster = Mockito.mock(MessageBroadcaster.class);
        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
        when(applicationContext.getBean(QuizxelSesh.class)).thenReturn(new QuizxelSesh(broadcaster));
        seshfactory = new SeshFactoryImpl(applicationContext);
        seshCode = "ABCD";
    }

    @Test
    void CREATE_GAME_WHEN_SUPPORTED_GAME_WITH_SERVICE_SHOULD_RETURN_GAME() {

        Sesh result = seshfactory.createSesh(seshCode, SeshType.QUIZXEL);
        assertEquals(QuizxelSesh.class, result.getClass());
    }

    @Test
    void CREATE_GAME_WHEN_NOT_SUPPORTED_GAME_WITH_SERVICE_SHOULD_THROW_EXCEPTION() {

        assertThrows(UnsupportedOperationException.class, () -> seshfactory.createSesh(seshCode, SeshType.UNKNOWN));
    }

}