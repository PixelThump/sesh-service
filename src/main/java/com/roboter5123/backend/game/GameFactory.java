package com.roboter5123.backend.game;
import com.roboter5123.backend.game.chat.ChatGame;
import org.springframework.stereotype.Component;

@Component
public class GameFactory {

    public Game createGame(String type) {

        Game game;
        if ("chat".equals(type)) {
            game = new ChatGame();
        } else {
            game = new ChatGame();
        }

        return game;
    }
}
