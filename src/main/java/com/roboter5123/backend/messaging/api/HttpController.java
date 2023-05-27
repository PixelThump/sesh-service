package com.roboter5123.backend.messaging.api;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;

public interface HttpController {

    String createSession(GameMode gameMode) throws TooManySessionsException;
}
