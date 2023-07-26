package com.pixelthump.seshservice.sesh.implementation;
import com.pixelthump.seshservice.messaging.api.MessageBroadcaster;
import com.pixelthump.seshservice.messaging.model.Command;
import com.pixelthump.seshservice.messaging.model.action.Action;
import com.pixelthump.seshservice.sesh.api.PlayerManager;
import com.pixelthump.seshservice.sesh.api.Sesh;
import com.pixelthump.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.pixelthump.seshservice.sesh.exception.PlayerNotInSeshException;
import com.pixelthump.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.pixelthump.seshservice.sesh.exception.SeshIsFullException;
import com.pixelthump.seshservice.sesh.model.SeshStage;
import com.pixelthump.seshservice.sesh.model.SeshType;
import com.pixelthump.seshservice.sesh.model.state.AbstractSeshState;
import com.pixelthump.seshservice.sesh.model.state.ControllerLobbyState;
import com.pixelthump.seshservice.sesh.model.state.HostLobbyState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;

@Log4j2
@ToString
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
    protected boolean isStarted;

    protected AbstractSeshBaseClass(MessageBroadcaster broadcaster, SeshType seshType, PlayerManager playerManager, Integer maxplayers) {

        this.broadcaster = broadcaster;
        this.seshType = seshType;
        this.lastInteractionTime = LocalDateTime.now();
        this.unprocessedCommands = new LinkedList<>();
        this.playerManager = playerManager;
        this.currentStage = SeshStage.LOBBY;
        isStarted = false;
        this.maxPlayers = maxplayers;
    }

    public void startSesh() {

        this.isStarted = true;
    }

    @Override
    public AbstractSeshState joinSeshAsHost(String socketId) throws PlayerAlreadyJoinedException {

        if (!playerManager.joinAsHost(socketId)) {

            throw new PlayerAlreadyJoinedException("Host has already joined this sesh");
        }

        this.lastInteractionTime = LocalDateTime.now();

        return this.getHostState();
    }

    @Override
    public AbstractSeshState joinSeshAsController(String playerName, String socketId) throws SeshIsFullException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException {

        if (this.playerManager.isSeshFull()) {

            throw new SeshIsFullException("A maximum of " + maxPlayers + " is allowed to join this Sesh.");
        }

        if (!this.playerManager.hasHostJoined()) {

            throw new SeshCurrentlyNotJoinableException("Host hasn't connected yet. Try again later.");
        }

        if (!this.playerManager.joinAsPlayer(playerName, socketId)) {

            throw new PlayerAlreadyJoinedException("Player with name " + playerName + " has already joined the Sesh");
        }

        this.lastInteractionTime = LocalDateTime.now();

        return this.getControllerState();
    }

    public AbstractSeshState getHostState() {

        if (this.currentStage == SeshStage.LOBBY) {

            return getHostLobbyState();

        } else {

            return getHostMainStageState();
        }
    }

    public AbstractSeshState getControllerState() {

        if (this.currentStage == SeshStage.LOBBY) {

            return getControllerLobbyState();

        } else {

            return getControllerMainStageState();
        }
    }

    protected HostLobbyState getHostLobbyState() {

        HostLobbyState state = new HostLobbyState();
        state.setMaxPlayers(this.maxPlayers);
        state.setSeshCode(this.seshCode);
        state.setPlayers(this.playerManager.getPlayers());
        state.setCurrentStage(this.currentStage);
        state.setHasVip(playerManager.hasVIP());
        return state;
    }

    protected ControllerLobbyState getControllerLobbyState() {

        ControllerLobbyState state = new ControllerLobbyState();
        state.setSeshCode(this.seshCode);
        state.setPlayers(this.playerManager.getPlayers());
        state.setCurrentStage(this.currentStage);
        state.setHasVip(this.playerManager.hasVIP());

        return state;
    }

    @Override
    public void addCommand(Command command) throws PlayerNotInSeshException {

        if (!this.playerManager.hasPlayerAlreadyJoinedByPlayerId(command.getPlayerId())) {

            throw new PlayerNotInSeshException(command.getPlayerId() + " hasn't joined the sesh.");
        }

        this.lastInteractionTime = LocalDateTime.now();
        this.unprocessedCommands.offer(command);
    }

    @Scheduled(fixedDelay = 33)
    public void processQueue() {

        if (!isStarted && !playerManager.hasHostJoined() && playerManager.getPlayers().isEmpty()) {

            return;
        }

        Deque<Command> queue = new LinkedList<>(this.unprocessedCommands);
        this.unprocessedCommands.clear();

        for (Command command : queue) {

            this.processCommand(command);
        }

        broadcastState();
    }

    protected void broadcastState() {

        if (this.currentStage == SeshStage.LOBBY){

            broadcastLobbyState();

        }else{

            broadcastMainStageState();
        }
    }

    protected void broadcastLobbyState(){

        broadcastToHost(getHostLobbyState());
        broadcastToAllControllers(getControllerLobbyState());
    }

    protected abstract void broadcastMainStageState();

    protected void broadcastToHost(Object payload) {

        this.broadcaster.broadcastSeshUpdateToHost(this.seshCode, payload);
    }

    protected void broadcastToAllControllers(Object payload) {

        this.broadcaster.broadcastSeshUpdateToControllers(this.seshCode, payload);
    }

    private void processCommand(Command command) {

        if (this.currentStage == SeshStage.LOBBY) {

            this.processLobbyCommand(command);

        } else if (this.currentStage == SeshStage.MAIN) {

            this.processMainCommand(command);
        }
    }

    private void processLobbyCommand(Command command) {

        String playerId = command.getPlayerId();
        Action<?> action = command.getAction();

        if (this.playerManager.isVIP(playerId) && action.getType().equals("startSesh")) {

            this.startMainStage();

        } else if ((this.playerManager.isVIP(playerId) || !this.playerManager.hasVIP()) && action.getType().equals("makeVip")) {

            this.playerManager.setVIP((String) action.getBody());
        }
    }

    /**
     * The main game logic resides in this method.
     *
     * @param command The command that should be processed.
     */
    protected abstract void processMainCommand(Command command);

    protected abstract void startMainStage();

    protected abstract AbstractSeshState getHostMainStageState();

    protected abstract AbstractSeshState getControllerMainStageState();
}