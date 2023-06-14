package com.roboter5123.play.backend.game.api;
import com.roboter5123.play.backend.messaging.model.Command;

import java.util.Map;

public interface Game {

    Map<String, Object> joinGame(String playerName);

    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    void setSessionCode(String sessionCode);

    String getSessionCode();

    void broadcast(Object joinCommand);

    void addCommand(Command command) throws UnsupportedOperationException;
}
