package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.exception.TooManySessionsException;

public interface GameSessionManager {

    Game getGameSession(String sessionCode) throws NoSuchSessionException;
    String createGameSession(GameMode gameMode, GameService service) throws TooManySessionsException;
}
