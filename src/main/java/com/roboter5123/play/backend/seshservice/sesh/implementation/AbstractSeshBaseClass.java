package com.roboter5123.play.backend.seshservice.sesh.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Action;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.MakeVIPAction;
import com.roboter5123.play.backend.seshservice.messaging.model.StartSeshAction;
import com.roboter5123.play.backend.seshservice.sesh.api.PlayerManager;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;

public abstract class AbstractSeshBaseClass implements Sesh {

    @Getter
    private final SeshType seshType;
    @Getter
    private final String seshCode;
    private final MessageBroadcaster broadcaster;
    @Getter
    protected LocalDateTime lastInteractionTime;
    protected final Deque<Command> unprocessedCommands;
    protected final PlayerManager playerManager;
    protected SeshStage currentStage;

    protected AbstractSeshBaseClass(String seshCode, MessageBroadcaster broadcaster, SeshType seshType, PlayerManager playerManager) {

        this.seshCode = seshCode;
        this.broadcaster = broadcaster;
        this.seshType = seshType;
        this.lastInteractionTime = LocalDateTime.now();
        this.unprocessedCommands = new LinkedList<>();
        this.playerManager = playerManager;
        this.currentStage = SeshStage.LOBBY;
    }

    @Override
    public void broadcastToHost(Object payload) {

        this.broadcaster.brodcastSeshUpdateToHost(this.seshCode, payload);
    }

    @Override
    public void broatcastToControllers(Object payload) {

        this.broadcaster.brodcastSeshUpdateToControllers(this.seshCode, payload);
    }

    @Override
    public void broadcastToAll(Object payload) {

        this.broadcaster.broadcastSeshUpdate(this.seshCode, payload);
    }

    protected void processLobbyCommand(Command command) {

        String playerName = command.getPlayer();
        Action action = command.getAction();

        if (this.playerManager.isVIP(playerName) && action instanceof StartSeshAction) {

            this.currentStage = SeshStage.QUIZ;

        } else if (action instanceof MakeVIPAction makeVIPAction) {

            this.playerManager.setVIP(makeVIPAction.getPlayerName());
        }
    }
}
