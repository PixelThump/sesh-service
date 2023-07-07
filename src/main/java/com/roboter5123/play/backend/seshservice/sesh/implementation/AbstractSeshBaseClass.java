package com.roboter5123.play.backend.seshservice.sesh.implementation;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import com.roboter5123.play.backend.seshservice.sesh.api.PlayerManager;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerNotInSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    @Override
    public Map<String, Object> joinSeshAsHost(String socketId) throws PlayerAlreadyJoinedException {

        if (!playerManager.joinAsHost(socketId)) {

            throw new PlayerAlreadyJoinedException("Host has already joined this sesh");
        }
        this.lastInteractionTime = LocalDateTime.now();
        return this.getState();
    }

    public Map<String, Object> getState() {

        if (this.currentStage == SeshStage.LOBBY) {

            return getLobbyState();

        } else if (this.currentStage == SeshStage.MAIN) {

            return getMainStageState();
        }
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> joinSeshAsController(String playerName, String socketId) throws SeshIsFullException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException {

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

    public void startSesh() {

        this.isStarted = true;
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

    protected void broadcastToAll(Object payload) {

        this.broadcaster.broadcastSeshUpdate(this.seshCode, payload);
    }

    protected Map<String, Object> getLobbyState() {

        Map<String, Object> state = new HashMap<>();
        state.put("players", this.playerManager.getPlayers());
        state.put("seshCode", this.getSeshCode());
        state.put("maxPlayers", maxPlayers);
        state.put("currentStage", this.currentStage);

        return state;
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

        if (this.playerManager.isVIP(playerId) && action.getType().equals("startSesh") && action.getBody().equals(true)) {

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

    protected abstract Map<String, Object> getMainStageState();

}
