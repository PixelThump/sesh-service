package com.roboter5123.play.backend.seshservice.messaging.api;
public interface MessageBroadcaster {

    void broadcastSeshUpdate(String seshcode, Object payload);

    void brodcastSeshUpdateToControllers(String seshcode, Object payload);
    void brodcastSeshUpdateToHost(String seshcode, Object payload);
}
