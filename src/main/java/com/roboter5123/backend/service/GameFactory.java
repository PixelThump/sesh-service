package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.game.chat.ChatGame;
import org.springframework.stereotype.Component;

@Component
public class GameFactory{

    public Game createGame(GameMode gameMode, GameService service) throws UnsupportedOperationException {

        Game game;

        if (service == null) {

            game = this.createGame(gameMode);

        } else {

            throw new UnsupportedOperationException("No game of game mode " + gameMode.name() + "is supported.");
        }

        return game;
    }

    public Game createGame(GameMode gameMode) throws UnsupportedOperationException {

        Game game;

        if (gameMode == GameMode.CHAT){

            game = new ChatGame();
        }else {

            throw new UnsupportedOperationException("No game of game mode " + gameMode.name() + "is supported.");
        }

        return game;
    }
}
