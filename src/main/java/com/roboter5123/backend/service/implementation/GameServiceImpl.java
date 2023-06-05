package com.roboter5123.backend.service.implementation;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.game.api.JoinUpdate;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.api.GameSessionManager;
import com.roboter5123.backend.service.api.MessageBroadcaster;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameSessionManager gameSessionManager;
    private final MessageBroadcaster broadcaster;
    private final Logger logger;

    @Autowired
    public GameServiceImpl(GameSessionManager gameSessionManager, MessageBroadcaster broadcaster) {

        this.gameSessionManager = gameSessionManager;
        this.broadcaster = broadcaster;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public Optional<Game> createSession(GameMode gameMode) {

        try {

            Game game = this.gameSessionManager.createGameSession(gameMode, this);
            return Optional.of(game);

        } catch (TooManySessionsException e) {

            logger.error("Unable to create session because there were too many sessions");
            return Optional.empty();
        }

    }

    @Override
    public Optional<Game> getGame(String sessionCode) {

        try {

            Game game = this.gameSessionManager.getGameSession(sessionCode);
            return Optional.of(game);

        } catch (NoSuchSessionException e) {

            this.logger.warn("No session with code {} exists!", sessionCode);
            return Optional.empty();
        }
    }

    @Override
    public Map<String, Object> joinGame(String sessionCode, String playerName) throws NoSuchSessionException {

        final Game game;

        try {

            game = this.gameSessionManager.getGameSession(sessionCode);

        } catch (NoSuchSessionException e) {

            logger.error("Could not join session with code {}. Session not found.", sessionCode);
            throw e;
        }

        final JoinUpdate joinUpdate = game.joinGame(playerName);

        this.broadcast(sessionCode, joinUpdate.getCommand());
        return joinUpdate.getGameState();
    }

    @Override
    public void broadcast(String sessionCode, Object payload) {

        this.broadcaster.broadcastGameUpdate(sessionCode, payload);
    }
}
