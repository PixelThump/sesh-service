package com.roboter5123.backend.game;
import java.util.Map;

public interface Game {

    Map<String, Object> joinGame(String playerName);
    void addCommand(Command command);
    GameState processCommands();
}
