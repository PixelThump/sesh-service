package com.roboter5123.play.backend.seshservice.implementation;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshFactory;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.sesh.implementation.SeshFactoryImpl;
import com.roboter5123.play.backend.seshservice.sesh.implementation.chat.ChatSesh;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeshFactoryImplTest {

    SeshFactory seshfactory;

    @BeforeEach
    void setUp() {

        MessageBroadcaster broadcaster = Mockito.mock(MessageBroadcaster.class);
        seshfactory = new SeshFactoryImpl(broadcaster);
    }

    @Test
    void CREATE_GAME_WHEN_SUPPORTED_GAME_WITH_SERVICE_SHOULD_RETURN_GAME() {

        Sesh result = seshfactory.createSesh(SeshType.CHAT);
        assertEquals(ChatSesh.class, result.getClass());
    }

    @Test
    void CREATE_GAME_WHEN_NOT_SUPPORTED_GAME_WITH_SERVICE_SHOULD_THROW_EXCEPTION() {

        assertThrows(UnsupportedOperationException.class, () -> seshfactory.createSesh(SeshType.UNKNOWN));
    }

}