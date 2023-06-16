package com.roboter5123.play.backend.service.implementation;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameFactory;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.service.api.GameSessionManager;
import com.roboter5123.play.backend.service.exception.NoSuchSessionException;
import com.roboter5123.play.backend.service.exception.TooManySessionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GameSessionManagerImpl implements GameSessionManager {

    private final Map<String, Game> games;
    private final Random random;
    private final GameFactory gameFactory;
    private final Logger logger;

    @Autowired
    public GameSessionManagerImpl(final GameFactory gameFactory, final Random random) {

        this.games = new HashMap<>();
        this.gameFactory = gameFactory;
        this.random = random;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public Game getGameSession(final String sessionCode) throws NoSuchSessionException {

        final Game game = this.games.get(sessionCode);

        if (game == null) {

            String errorMessage = "Could not join session with code " + sessionCode + ".Session not found.";
            logger.error(errorMessage);
            throw new NoSuchSessionException(errorMessage);
        }

        return game;
    }

    @Override
    public Game createGameSession(final GameMode gameMode) throws TooManySessionsException {

        final Game game = gameFactory.createGame(gameMode);

        String sessionCode = createSessionCode();
        game.setSessionCode(sessionCode);

        this.games.put(sessionCode, game);
        return game;
    }

    private String createSessionCode() throws TooManySessionsException {

        final int LETTER_A_NUMBER = 65;
        final int LETTER_Z_NUMBER = 90;
        final int codeLength = 4;

        if (games.size() >= (Math.pow(LETTER_Z_NUMBER - (double) LETTER_A_NUMBER, codeLength))) {

            String errorMessage = "Unable to create session because there were too many sessions";
            logger.error(errorMessage);
            throw new TooManySessionsException(errorMessage);
        }

        String sessionCode;

        do {

            sessionCode = random.ints(LETTER_A_NUMBER, LETTER_Z_NUMBER + 1).limit(codeLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        } while (this.games.containsKey(sessionCode));

        return sessionCode;
    }
}
