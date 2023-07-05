package com.roboter5123.play.backend.seshservice.messaging.api;
public interface MessageBroadcaster {

    void broadcastSeshUpdate(String seshcode, Object payload);

    void broadcastSeshUpdateToControllers(String seshcode, Object payload);

    void broadcastSeshUpdateToHost(String seshcode, Object payload);
}
