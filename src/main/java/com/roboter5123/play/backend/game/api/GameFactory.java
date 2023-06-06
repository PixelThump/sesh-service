package com.roboter5123.play.backend.game.api;
public interface GameFactory {

    Game createGame(GameMode gameMode) throws UnsupportedOperationException;
}
