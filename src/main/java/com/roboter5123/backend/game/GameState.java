package com.roboter5123.backend.game;
import java.util.Map;

public interface GameState<T>{

    Message<String> getJoinMessage();

    Message<Map<String,T>> getStateMessage();
}
