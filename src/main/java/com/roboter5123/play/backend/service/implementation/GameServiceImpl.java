package com.roboter5123.play.backend.service.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.service.api.GameService;
import com.roboter5123.play.backend.service.api.GameSessionManager;
import com.roboter5123.play.backend.service.exception.NoSuchSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    private final GameSessionManager gameSessionManager;

    @Autowired
    public GameServiceImpl(GameSessionManager gameSessionManager) {

        this.gameSessionManager = gameSessionManager;
    }

    @Override
    public Game createSession(GameMode gameMode) {

        return this.gameSessionManager.createGameSession(gameMode);
    }

    @Override
    public Game getGame(String sessionCode) throws NoSuchSessionException {

        return this.gameSessionManager.getGameSession(sessionCode);
    }

    @Override
    public void sendCommandToGame(final CommandStompMessage message, final String sessionCode) throws NoSuchSessionException {

        final Game game = getGame(sessionCode);
        game.addCommand(message.getCommand());
    }

    @Override
    public Map<String, Object> joinGame(String sessionCode, String playerName) throws NoSuchSessionException {

        final Game game = this.getGame(sessionCode);
        return game.joinGame(playerName);
    }
}
