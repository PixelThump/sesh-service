package com.roboter5123.play.backend.game.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameFactory;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.game.implementation.chat.ChatGame;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameFactoryImplTest {

    GameFactory gameFactory;

    @BeforeEach
    void setUp() {

        MessageBroadcaster broadcaster = Mockito.mock(MessageBroadcaster.class);
        gameFactory = new GameFactoryImpl(broadcaster);
    }

    @Test
    void CREATE_GAME_WHEN_SUPPORTED_GAME_WITH_SERVICE_SHOULD_RETURN_GAME() {

        Game result = gameFactory.createGame(GameMode.CHAT);
        assertEquals(ChatGame.class, result.getClass());
    }

    @Test
    void CREATE_GAME_WHEN_NOT_SUPPORTED_GAME_WITH_SERVICE_SHOULD_THROW_EXCEPTION() {

        assertThrows(UnsupportedOperationException.class, () -> gameFactory.createGame(GameMode.UNKNOWN));
    }

}