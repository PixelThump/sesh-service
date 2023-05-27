package com.roboter5123.backend.service.api;
public interface MessageBroadcaster {

    void broadcastGameUpdate(String sessionCode, Object payload);
}
