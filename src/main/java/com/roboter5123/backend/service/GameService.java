package com.roboter5123.backend.service;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.JoinPayloads;

public interface GameService {

    JoinPayloads joinGame(String gameCode, String playerName) throws NoSuchSessionException;

    void broadcastGameUpdate(String sessionCode, Object payload);

}
