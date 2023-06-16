package com.roboter5123.play.backend.api.api;
import com.roboter5123.play.backend.api.model.exception.BadRequestException;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.api.model.HttpGameDTO;
import com.roboter5123.play.backend.api.model.exception.NoSuchSessionHttpException;
import com.roboter5123.play.backend.api.model.exception.TooManySessionsHttpException;

import java.util.List;

public interface HttpController {

    List<GameMode> getGameModes();

    HttpGameDTO createSession(String gameModeString) throws TooManySessionsHttpException, BadRequestException;

    HttpGameDTO getGame(String sessionCode) throws NoSuchSessionHttpException;
}
