package com.roboter5123.backend.game.api;
public interface Game {

    JoinUpdate joinGame(String playerName);
    GameMode getGameMode();
    void setGameMode(GameMode gameMode);
    void setSessionCode(String sessionCode);
    String getSessionCode();
}
