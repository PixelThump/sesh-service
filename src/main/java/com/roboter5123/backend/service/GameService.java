package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;

public interface GameService {

    GameState<Object> joinGame(String gameCode, String playerName);
    void addCommand(String gameCode, Command command);
    GameState<Object> updateGameState(String gameCode);
}
