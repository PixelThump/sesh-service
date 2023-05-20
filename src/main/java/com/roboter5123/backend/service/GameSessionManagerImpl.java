package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.game.GameMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class GameSessionManagerImpl implements GameSessionManager {

    private final Map<String, Game> games;
    Random random;
    private final GameFactory gameFactory;


    public GameSessionManagerImpl(GameFactory gameFactory, Random random) {

        this.games = new HashMap<>();
        this.gameFactory = gameFactory;
        this.random = random;

    }

    @Override
    public Game getGameSession(String sessionCode) throws NoSuchSessionException {

        try {
            Game game = this.games.get(sessionCode);

            if (game == null) {

                throw new NullPointerException();
            }

            return game;

        } catch (NullPointerException e) {

            throw new NoSuchSessionException("So Session with code " + sessionCode + "exists.");
        }

    }

    @Override
    public String createGameSession(GameMode gameMode) {

        Game game = gameFactory.createGame(gameMode);
        String sessionCode = createSessionCode();

        this.games.put(sessionCode, game);

        return sessionCode;
    }

    private String createSessionCode() {

        final int LETTER_A_NUMBER = 97;
        final int LETTER_Z_NUMBER = 122;
        final int codeLength= 4;

        return random.ints(LETTER_Z_NUMBER, LETTER_A_NUMBER + 1)
                .limit(codeLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
