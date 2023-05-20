package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.chat.ChatGame;
import com.roboter5123.backend.game.GameMode;
import org.springframework.stereotype.Component;

@Component
public class GameFactory{

    public Game createGame(GameMode gameMode) {

        Game game;
        if (gameMode == GameMode.CHAT) {
            game = new ChatGame();
        } else {
            game = new ChatGame();
        }

        return game;
    }
}
