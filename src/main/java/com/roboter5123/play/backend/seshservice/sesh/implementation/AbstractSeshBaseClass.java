package com.roboter5123.play.backend.seshservice.sesh.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import lombok.Getter;

import java.time.LocalDateTime;

public abstract class AbstractSeshBaseClass implements Sesh {

    @Getter
    private final SeshType seshType;
    @Getter
    private final String seshCode;
    private final MessageBroadcaster broadcaster;
    @Getter
    protected LocalDateTime lastInteractionTime;

    protected AbstractSeshBaseClass(String seshCode, MessageBroadcaster broadcaster, SeshType seshType) {

        this.seshCode = seshCode;

        this.broadcaster = broadcaster;
        this.seshType = seshType;
        this.lastInteractionTime = LocalDateTime.now();
    }

    @Override
    public void broadcastToHost(Object payload) {

    }

    @Override
    public void broatcastToControllers(Object payload) {

        this.broadcaster.brodcastSeshUpdateToControllers(this.seshCode, payload);
    }

    @Override
    public void broadcastToAll(Object payload) {

        this.broadcaster.broadcastSeshUpdate(this.seshCode, payload);
    }
}
