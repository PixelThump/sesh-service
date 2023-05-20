package com.roboter5123.backend.service;
import com.roboter5123.backend.service.exception.NoSuchSessionException;

import java.util.Map;

public interface GameService {

    Map<String, Object> joinGame(String gameCode, String playerName) throws NoSuchSessionException;
    void broadcastPeriodicGameUpdate(String sessionCode, Object payload);
}
