package com.roboter5123.play.backend.seshservice.sesh.model;
public interface Player {

    String getPlayerName();
    boolean getVip();
    String getPlayerId();
    Long getPoints();
    Long addPoints(Long points);
}
