package com.roboter5123.backend.service;
import com.roboter5123.backend.service.model.Command;

public interface GameService {

    void addCommand(Command command, String gameCode);
}
