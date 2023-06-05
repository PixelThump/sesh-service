package com.roboter5123.backend.game.api;
import java.util.Map;

public interface Game {

    Map<String, Object> joinGame(String playerName);

    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    void setSessionCode(String sessionCode);

    String getSessionCode();

    void broadcast(Object joinCommand);
}
