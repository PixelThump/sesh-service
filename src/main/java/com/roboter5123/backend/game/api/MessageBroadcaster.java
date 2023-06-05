package com.roboter5123.backend.game.api;
public interface MessageBroadcaster {

    void broadcastGameUpdate(String sessionCode, Object payload);
}
