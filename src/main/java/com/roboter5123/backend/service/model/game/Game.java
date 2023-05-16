package com.roboter5123.backend.service.model.game;
import com.roboter5123.backend.service.model.Command;

public interface Game {

    void addCommand(Command command);
    GameState processCommands();
}
