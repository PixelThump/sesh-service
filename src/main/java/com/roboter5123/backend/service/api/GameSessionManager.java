package com.roboter5123.backend.service.api;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.exception.TooManySessionsException;

public interface GameSessionManager {

    Game getGameSession(String sessionCode) throws NoSuchSessionException;

    Game createGameSession(GameMode gameMode) throws TooManySessionsException;
}
