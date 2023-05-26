package com.roboter5123.backend.game.api;
public interface Game {

    JoinUpdate joinGame(String playerName);
    GameMode getGameMode();
}
