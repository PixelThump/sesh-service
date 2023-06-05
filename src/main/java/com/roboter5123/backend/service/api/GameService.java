package com.roboter5123.backend.service.api;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;

import java.util.Map;
import java.util.Optional;

public interface GameService {

    Optional<Game> createSession(GameMode gameMode) throws TooManySessionsException;

    Map<String, Object> joinGame(String sessionCode, String playerName) throws NoSuchSessionException;

    Optional<Game> getGame(String sessionCode);

}
