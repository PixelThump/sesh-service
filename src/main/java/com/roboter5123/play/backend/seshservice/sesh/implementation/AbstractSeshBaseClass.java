package com.roboter5123.play.backend.seshservice.sesh.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.api.PlayerManager;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshLobby;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerNotInSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractSeshBaseClass implements Sesh {

    @Getter
    private final SeshType seshType;
    private final Integer maxPlayers;
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

    protected AbstractSeshBaseClass(MessageBroadcaster broadcaster, SeshType seshType, PlayerManager playerManager, Integer maxplayers) {

        this.broadcaster = broadcaster;
        this.seshType = seshType;
        this.lastInteractionTime = LocalDateTime.now();
        this.unprocessedCommands = new LinkedList<>();
        this.playerManager = playerManager;
        this.currentStage = SeshStage.LOBBY;
        isStarted = false;
        lobby = new SeshLobbyImplementation(playerManager);
        this.maxPlayers = maxplayers;
    }

    protected void broadcastToAll(Object payload) {

        this.broadcaster.broadcastSeshUpdate(this.seshCode, payload);
    }

    @Override
    public Map<String, Object> joinSeshAsHost() throws PlayerAlreadyJoinedException {

        if (!playerManager.joinAsHost()) {

            throw new PlayerAlreadyJoinedException("Host has already joined this sesh");
        }
        this.lastInteractionTime = LocalDateTime.now();
        return this.getState();
    }

    @Override
    public Map<String, Object> joinSeshAsController(String playerName) throws SeshIsFullException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException {

        if (this.playerManager.isSeshFull()) {

            throw new SeshIsFullException("A maximum of " + maxPlayers + " is allowed to join this Sesh.");
        }

        if (!this.playerManager.hasHostJoined()) {

            throw new SeshCurrentlyNotJoinableException("Host hasn't connected yet. Try again later.");
        }

        if (!this.playerManager.joinAsPlayer(playerName)) {

            throw new PlayerAlreadyJoinedException("Player with name " + playerName + " has already joined the Sesh");
        }

        this.lastInteractionTime = LocalDateTime.now();
        return this.getState();
    }

    @Override
    public void addCommand(Command command) throws PlayerNotInSeshException {

        if (!this.playerManager.hasPlayerAlreadyJoinedByPlayerId(command.getPlayerId())) {

            throw new PlayerNotInSeshException(command.getPlayerId() + " hasn't joined the sesh.");
        }

        this.lastInteractionTime = LocalDateTime.now();
        this.unprocessedCommands.offer(command);
    }

    @Scheduled(fixedDelay = 30000)
    public void processQueue() {

        if (!isStarted && !playerManager.hasHostJoined() && playerManager.getPlayers().isEmpty()) {

            return;
        }

        Deque<Command> queue = new LinkedList<>(this.unprocessedCommands);
        this.unprocessedCommands.clear();

        for (Command command : queue) {

            this.processCommand(command);
        }

        this.broadcastToAll(getState());
    }

    private void processCommand(Command command) {

        if (this.currentStage == SeshStage.LOBBY) {

            this.processLobbyCommand(command);

        } else if (this.currentStage == SeshStage.MAIN) {

            this.processMainCommand(command);
        }
    }

    protected void processLobbyCommand(Command command) {

        if (lobby.processLobbyCommand(command)) {

            this.currentStage = SeshStage.MAIN;
        }
    }

    protected abstract void processMainCommand(Command command);

    public void startSesh() {

        this.isStarted = true;
    }

    protected abstract Map<String, Object> getState();
}
