package com.pixelthump.seshservice.sesh.api;
public interface Player {

    String getPlayerName();
    boolean getVip();
    String getPlayerId();
    Long addPoints(Long points);
}
