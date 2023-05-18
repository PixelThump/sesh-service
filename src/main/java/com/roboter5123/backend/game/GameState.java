package com.roboter5123.backend.game;
import java.util.Map;


public interface GameState{

    Command getLastCommand();

    Map<String,Object> getState();
}
