package com.roboter5123.backend.service.api;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import com.roboter5123.backend.service.model.JoinPayloads;

public interface GameService {

    String createSession(GameMode gameMode) throws TooManySessionsException;
    JoinPayloads joinGame(String gameCode, String playerName) throws NoSuchSessionException;

    void broadcastGameUpdate(String sessionCode, Object payload);

}
