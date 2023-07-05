package com.roboter5123.play.backend.seshservice.sesh.api;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelPlayer;

import java.util.List;

public interface PlayerManager {

    /**
     * @param playerId The id the Host should receive
     * @return True if join was successful. False if not.
     */
    boolean joinAsHost(String playerId);

    /**
     * @param playerId The id the player should receive
     * @return True if join was successful. False if not.
     */
    boolean joinAsPlayer(String playerName, String playerId);

    /**
     * @param playerId The id that should be checked
     * @return True if a player with this id has joined already. False if not.
     */
    boolean hasPlayerAlreadyJoinedByPlayerId(String playerId);

    /**
     * @return True if the host has joined already. False if not.
     */
    boolean hasHostJoined();

    /**
     * @return True if sesh is full. False if not.
     */
    boolean isSeshFull();

    /**
     * @return A list of players rthat have joined the sesh. Does not include host.
     */
    List<QuizxelPlayer> getPlayers();

    /**
     * @param playerId The playerId that should be checked for vip status
     * @return True if player is vip. False if not.
     */
    boolean isVIP(String playerId);

    /**
     * @param playerId The id off the player that should be made vip if possible
     * @return True if the player was set as vip. False if not.
     */
    boolean setVIP(String playerId);

    /**
     * @return True if a vip exists. False if not.
     */
    boolean hasVIP();
}
