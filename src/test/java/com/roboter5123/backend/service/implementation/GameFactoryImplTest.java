package com.roboter5123.backend.service.implementation;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameFactory;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.game.implementation.chat.ChatGame;
import com.roboter5123.backend.service.api.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GameFactoryImplTest {

    @Autowired
    GameFactory gameFactory;
    @Autowired
    GameService service;

    @Test
    void CREATE_GAME_WHEN_SUPPORTED_GAME_WITH_SERVICE_SHOULD_RETURN_GAME() {

        Game result = gameFactory.createGame(GameMode.CHAT, service);
        assertEquals(ChatGame.class, result.getClass());
    }

    @Test
    void CREATE_GAME_WHEN_NOT_SUPPORTED_GAME_WITH_SERVICE_SHOULD_THROW_EXCEPTION() {

        assertThrows(UnsupportedOperationException.class, () -> gameFactory.createGame(GameMode.UNKNOWN,service));
    }

}