package com.roboter5123.backend.api.api;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.api.model.HttpGameDTO;
import com.roboter5123.backend.api.model.exception.NoSuchSessionHttpException;
import com.roboter5123.backend.api.model.exception.TooManySessionsHttpException;

public interface HttpController {

    HttpGameDTO createSession(GameMode gameMode) throws TooManySessionsHttpException;

    HttpGameDTO getGame(String sessionCode) throws NoSuchSessionHttpException;
}
