package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameFactory;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    Map<String, Game> games;
    GameFactory factory;

    @Autowired
    public GameServiceImpl(GameFactory factory) {

        this.games = new HashMap<>();
        this.factory = factory;
        this.games.put("a", factory.createGame("chat"));
    }

    @Override
    public GameState joinGame(String gameCode, String playerName) throws NoSuchSessionException {

        Game game = this.games.get(gameCode);

        if (game == null) {

            throw new NoSuchSessionException();
        }
        return game.joinGame(playerName);
    }

    @Override
    public void addCommand(String gameCode, Command command) {
//Todo: Implement
    }

    @Override
    public GameState updateGameState(String gameCode) {
//Todo: Implement
        return null;
    }
}
