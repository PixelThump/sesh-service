package com.roboter5123.backend.service.api;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;

import java.util.Map;

public interface GameService {

    String createSession(GameMode gameMode) throws TooManySessionsException;
    Map<String, Object> joinGame(String gameCode, String playerName) throws NoSuchSessionException;

    void broadcast(String sessionCode, Object payload);

}
