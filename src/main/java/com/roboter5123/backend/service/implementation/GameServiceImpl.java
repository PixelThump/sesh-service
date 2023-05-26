package com.roboter5123.backend.service.implementation;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.game.api.JoinUpdate;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.api.GameSessionManager;
import com.roboter5123.backend.service.model.JoinPayloads;
import com.roboter5123.backend.service.model.ServiceCommand;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final GameSessionManager gameSessionManager;
    private final Logger logger;

    @Autowired
    public GameServiceImpl(GameSessionManager gameSessionManager) {

        this.gameSessionManager = gameSessionManager;
        logger = LoggerFactory.getLogger(this.getClass());

        final String debugSessionCode = createSession(GameMode.CHAT);
        logger.debug("Created debug chat session with code: {}", debugSessionCode);
    }

    @Override
    public String createSession(GameMode gameMode) throws TooManySessionsException {

        try {

            return this.gameSessionManager.createGameSession(gameMode, this);
        } catch (TooManySessionsException e) {
            logger.error("Unable to create session because there were too many sessions");
            throw e;
        }

    }

    @Override
    public JoinPayloads joinGame(String sessionCode, String playerName) throws NoSuchSessionException {

        final Game game;

        try {

            game = this.gameSessionManager.getGameSession(sessionCode);

        } catch (NoSuchSessionException e) {

            logger.error("Could not join session with code {}. Session not found.", sessionCode);
            throw e;
        }

        final JoinUpdate joinUpdate = game.joinGame(playerName);

        final JoinPayloads payloads = new JoinPayloads();
        payloads.setReply(joinUpdate.getGameState());

        final ServiceCommand broadcast = new ServiceCommand();
        broadcast.setPlayer(joinUpdate.getCommand().getPlayer());
        broadcast.setAction(joinUpdate.getCommand().getAction());

        payloads.setBroadcast(joinUpdate.getCommand());

        return payloads;
    }
}
