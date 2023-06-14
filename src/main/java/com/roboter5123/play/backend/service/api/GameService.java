package com.roboter5123.play.backend.service.api;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.messaging.model.CommandStompMessage;
import com.roboter5123.play.backend.service.exception.NoSuchSessionException;
import com.roboter5123.play.backend.service.exception.TooManySessionsException;

import java.util.Map;

public interface GameService {

    Game createSession(GameMode gameMode) throws TooManySessionsException;

    Map<String, Object> joinGame(String sessionCode, String playerName) throws NoSuchSessionException;

    Game getGame(String sessionCode) throws NoSuchSessionException;

    void sendCommandToGame(CommandStompMessage message, String sessionCode) throws NoSuchSessionException;
}
