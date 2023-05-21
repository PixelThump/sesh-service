package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.chat.ChatGame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GameFactoryTest {

    @Autowired
    GameFactory gameFactory;
    @Autowired
    GameService service;

    @Test
    void CREATEGAME_WHEN_SUPPORTED_GAME_WITHOUT_SERVICE_SHOULD_RETURN_GAME() {

        Game result = gameFactory.createGame(GameMode.CHAT);
        assertEquals(ChatGame.class, result.getClass());
    }

    @Test
    void CREATEGAME_WHEN_SUPPORTED_GAME_WITH_SERVICE_SHOULD_RETURN_GAME() {

        Game result = gameFactory.createGame(GameMode.CHAT, null);
        assertEquals(ChatGame.class, result.getClass());
    }

    @Test
    void CREATEGAME_WHEN_NOT_SUPPORTED_GAME_WITH_NO_SERVICE_SHOULD_THROW_EXCEPTION() {

        assertThrows(UnsupportedOperationException.class, () -> gameFactory.createGame(GameMode.ZELDA));
    }

    @Test
    void CREATEGAME_WHEN_NOT_SUPPORTED_GAME_WITH_SERVICE_SHOULD_THROW_EXCEPTION() {

        assertThrows(UnsupportedOperationException.class, () -> gameFactory.createGame(GameMode.ZELDA,service));
    }

}