package com.roboter5123.play.backend.webinterface.service.api;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.webinterface.service.exception.NoSuchSessionException;
import com.roboter5123.play.backend.webinterface.service.exception.TooManySessionsException;

public interface GameSessionManager {

    Game getGameSession(String sessionCode) throws NoSuchSessionException;

    Game createGameSession(GameMode gameMode) throws TooManySessionsException;
}
