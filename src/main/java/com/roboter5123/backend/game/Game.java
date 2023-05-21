package com.roboter5123.backend.game;
public interface Game {

    JoinUpdate joinGame(String playerName);
    GameMode getGameMode();
}
