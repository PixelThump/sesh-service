package com.roboter5123.play.backend.seshservice.messaging.api;
import java.util.List;

public interface MessageBroadcaster {

    void broadcastSeshUpdate(String seshcode, Object payload);

    void broadcastSeshUpdateToControllers(String seshcode, Object payload);

    void broadcastSeshUpdateToHost(String seshcode, Object payload);

    void broadcastToPlayer(String socketId, Object payload);

    void broadcastToPlayers(List<String> playerIds, Object payload);
}
