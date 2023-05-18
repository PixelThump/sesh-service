package com.roboter5123.backend.service;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.service.model.StompMessage;

import java.util.Map;

public interface GameService {

    Map<String, StompMessage> joinGame(String gameCode, String playerName);
    void addCommand(String gameCode, Command command);
    GameState updateGameState(String gameCode);
}
