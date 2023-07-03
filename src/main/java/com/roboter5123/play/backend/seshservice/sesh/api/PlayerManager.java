package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelPlayer;

import java.util.List;

public interface PlayerManager {

    boolean joinAsHost();

    boolean joinAsPlayer(String playerName);

    boolean hasPlayerAlreadyJoinedByPlayerId(String playerId);

    boolean hasHostJoined() throws PlayerAlreadyJoinedException;

    boolean isSeshFull();

    List<QuizxelPlayer> getPlayers();

    boolean isVIP(String playerName);

    boolean setVIP(String playerName);

    boolean hasVIP();
}
