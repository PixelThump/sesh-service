package com.roboter5123.backend.game.api;
public interface GameFactory {

    Game createGame(GameMode gameMode) throws UnsupportedOperationException;
}
