package com.roboter5123.backend.messaging.api;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.messaging.model.HttpGameDTO;
import com.roboter5123.backend.messaging.model.exception.NoSuchSessionHttpException;
import com.roboter5123.backend.messaging.model.exception.TooManySessionsHttpException;

public interface HttpController {

    HttpGameDTO createSession(GameMode gameMode) throws TooManySessionsHttpException;

    HttpGameDTO getGame(String sessionCode) throws NoSuchSessionHttpException;
}
