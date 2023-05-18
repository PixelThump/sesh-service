package com.roboter5123.backend.game;
public interface Game {

    GameState joinGame(String playerName);
    void addCommand(Command command);
    GameState processCommands();
}
