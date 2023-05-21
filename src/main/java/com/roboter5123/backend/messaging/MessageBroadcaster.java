package com.roboter5123.backend.messaging;
public interface MessageBroadcaster {

    void broadcastGameUpdate(String sessionCode, Object payload);
}
