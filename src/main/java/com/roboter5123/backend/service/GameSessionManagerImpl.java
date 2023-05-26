package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.exception.TooManySessionsException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class GameSessionManagerImpl implements GameSessionManager {

    private final Map<String, Game> games;
    private final Random random;
    private final GameFactory gameFactory;

    public GameSessionManagerImpl(final GameFactory gameFactory, final Random random) {

        this.games = new HashMap<>();
        this.gameFactory = gameFactory;
        this.random = random;

    }

    @Override
    public Game getGameSession(final String sessionCode) throws NoSuchSessionException {

        try {
            final Game game = this.games.get(sessionCode);

            if (game == null) {

                throw new NullPointerException();
            }

            return game;

        } catch (NullPointerException e) {

            throw new NoSuchSessionException("So Session with code " + sessionCode + "exists.");
        }

    }

    @Override
    public String createGameSession(final GameMode gameMode, final GameService service) throws TooManySessionsException {

        final Game game = gameFactory.createGame(gameMode, service);
        String sessionCode = createSessionCode();
        this.games.put(sessionCode, game);

        return sessionCode;
    }

    private String createSessionCode() throws TooManySessionsException {

        final int LETTER_A_NUMBER = 97;
        final int LETTER_Z_NUMBER = 122;
        final int codeLength = 4;

        if (games.size() >= (Math.pow(LETTER_Z_NUMBER - (double)LETTER_A_NUMBER, codeLength))){

            throw new TooManySessionsException();
        }

            String sessionCode;
        do {
            sessionCode = random.ints(LETTER_A_NUMBER, LETTER_Z_NUMBER + 1)
                    .limit(codeLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } while (this.games.containsKey(sessionCode));

        return sessionCode;
    }
}
