package com.roboter5123.backend.game;
import java.util.Map;

//TODO: Split this into 2 classes. One that actualy represents the gamestate internaly and one that gets sent to the frotnend
public interface GameState<T>{

    Message<String> getJoinMessage();

    Message<Map<String,T>> getStateMessage();
}
