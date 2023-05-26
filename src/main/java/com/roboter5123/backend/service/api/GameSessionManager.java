package com.roboter5123.backend.service.api;
import com.roboter5123.backend.game.api.Game;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;

public interface GameSessionManager {

    Game getGameSession(String sessionCode) throws NoSuchSessionException;
    String createGameSession(GameMode gameMode, GameService service) throws TooManySessionsException;
}
