package com.roboter5123.play.backend.messaging.api;
public interface MessageBroadcaster {

    void broadcastGameUpdate(String sessionCode, Object payload);
}
