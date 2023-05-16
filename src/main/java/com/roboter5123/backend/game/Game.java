package com.roboter5123.backend.game;
public interface Game {

    GameState<Object> joinGame(String playerName);
    void addCommand(Command command);
    GameState<Object> processCommands();
}
