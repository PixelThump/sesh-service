package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.chat.ChatGame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameFactoryTest {

    @Autowired
    GameFactory gameFactory;

    @Autowired
    GameService service;

    @Test
    void createGame() {

        Game result = gameFactory.createGame(GameMode.CHAT, service);

        assertEquals(ChatGame.class, result.getClass());

        result = gameFactory.createGame(GameMode.CHAT,null);

        assertEquals(ChatGame.class, result.getClass());
    }
}