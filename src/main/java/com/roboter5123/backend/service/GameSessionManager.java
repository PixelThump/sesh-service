package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Game;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.GameMode;

public interface GameSessionManager {

    Game getGameSession(String sessionCode) throws NoSuchSessionException;
    String createGameSession(GameMode gameMode);
}
