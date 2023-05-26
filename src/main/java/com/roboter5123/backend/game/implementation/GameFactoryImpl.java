package com.roboter5123.backend.game.implementation;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameFactory;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.game.implementation.chat.ChatGame;
import com.roboter5123.backend.service.api.GameService;
import org.springframework.stereotype.Component;

@Component
public class GameFactoryImpl implements GameFactory {

    public Game createGame(GameMode gameMode, GameService service) throws UnsupportedOperationException {

        final Game game;

        if (gameMode == GameMode.CHAT) {

            game = new ChatGame();

        } else {

            throw new UnsupportedOperationException("No game of game mode " + gameMode.name() + "is supported.");
        }

        return game;
    }
}
