package com.roboter5123.play.backend.seshservice.sesh.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.api.PlayerManager;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshLobby;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;

public abstract class AbstractSeshBaseClass implements Sesh {

    @Getter
    private final SeshType seshType;
    @Getter
    @Setter
    private String seshCode;
    private final MessageBroadcaster broadcaster;
    @Getter
    protected LocalDateTime lastInteractionTime;
    protected final Deque<Command> unprocessedCommands;
    protected final PlayerManager playerManager;
    @Getter
    protected SeshStage currentStage;
    protected SeshLobby lobby;
    protected boolean isStarted;

    protected AbstractSeshBaseClass(MessageBroadcaster broadcaster, SeshType seshType, PlayerManager playerManager) {

        this.broadcaster = broadcaster;
        this.seshType = seshType;
        this.lastInteractionTime = LocalDateTime.now();
        this.unprocessedCommands = new LinkedList<>();
        this.playerManager = playerManager;
        this.currentStage = SeshStage.LOBBY;
        isStarted = false;
        lobby = new SeshLobbyImplementation(playerManager);
    }

    protected void broadcastToAll(Object payload) {

        this.broadcaster.broadcastSeshUpdate(this.seshCode, payload);
    }

    protected void processLobbyCommand(Command command) {

        if (lobby.processLobbyCommand(command)) {

            this.currentStage = SeshStage.MAIN;
        }
    }

    public void startSesh() {

        this.isStarted = true;
    }
}
