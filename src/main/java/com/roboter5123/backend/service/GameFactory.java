package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.chat.ChatGame;
import org.springframework.stereotype.Component;

@Component
public class GameFactory{

    public Game createGame(GameMode gameMode, GameService service) {

        Game game;
        if (gameMode == GameMode.CHAT && service!= null) {

            game = new ChatGame();

        } else {

            throw new UnsupportedOperationException("");
        }

        return game;
    }
}
