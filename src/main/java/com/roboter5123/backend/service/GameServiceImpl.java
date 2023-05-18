package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.ErrorStompMessage;
import com.roboter5123.backend.service.model.StompMessage;
import com.roboter5123.backend.service.model.StompMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    private final Map<String, Game> games;
    private final GameFactory gameFactory;
    private final StompMessageFactory messageFactory;

    @Autowired
    public GameServiceImpl(GameFactory gameFactory, StompMessageFactory messageFactory) {

        this.messageFactory = messageFactory;

        this.games = new HashMap<>();
        this.gameFactory = gameFactory;
        this.games.put("a", this.gameFactory.createGame("chat"));
    }

    @Override
    public Map<String, StompMessage> joinGame(String gameCode, String playerName) throws NoSuchSessionException {

        Game game = this.games.get(gameCode);

        if (game == null) {

            NoSuchSessionException exception = new NoSuchSessionException("No such Session exists");
            ErrorStompMessage message = messageFactory.getMessage(exception);
            Map<String,StompMessage> messages = new HashMap<>();
            messages.put("error", message);
            return messages;
        }

        GameState state = game.joinGame(playerName);

        Map<String,StompMessage> messages = new HashMap<>();
        messages.put("reply", messageFactory.getMessage(state));
        messages.put("broadcast", messageFactory.getMessage(state.getLastCommand()));
        return messages;
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
