package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelJoinAction;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Log4j2
@ToString
public class QuizxelSesh extends AbstractSeshBaseClass {

    private static final Integer MAXPLAYERS = 5;
    private final Deque<Command> unprocessedCommands = new LinkedList<>();
    private final QuizxelPlayerManager playerManager;

    public QuizxelSesh(String seshCode, MessageBroadcaster broadcaster) {

        super(seshCode, broadcaster, SeshType.QUIZXEL);
        this.playerManager = new QuizxelPlayerManager(MAXPLAYERS);

    }

    private void broadcastJoinCommand(QuizxelJoinAction action) {

        Command command = new Command("Server", action);
        this.broadcast(command);
    }

    private Map<String, Object> getState() {

        Map<String, Object> state = new HashMap<>();
        state.put("players", this.playerManager.getPlayers());
        state.put("maxPlayers", MAXPLAYERS);

        return state;
    }

    @Override
    public Map<String, Object> joinSeshAsHost() throws PlayerAlreadyJoinedException {

        if (!playerManager.joinAsHost()) {

            throw new PlayerAlreadyJoinedException("Host has already joined this sesh");
        }
        return this.getState();
    }

    @Override
    public Map<String, Object> joinSeshAsController(String playerName) throws SeshIsFullException, PlayerAlreadyJoinedException, SeshCurrentlyNotJoinableException {

        if (this.playerManager.isSeshFull()) {

            throw new SeshIsFullException("A maximum of " + MAXPLAYERS + " is allowed to join this Sesh.");
        }

        if (!this.playerManager.addPlayerToSesh(playerName)) {

            throw new PlayerAlreadyJoinedException("Player with name " + playerName + " has already joined the Sesh");
        }

        if (!this.playerManager.hasHostJoined()) {

            throw new SeshCurrentlyNotJoinableException("Host hasn't connected yet. Try again later.");
        }

        broadcastJoinCommand(new QuizxelJoinAction(playerName));
        return this.getState();
    }

    @Override
    public void addCommand(Command command) throws UnsupportedOperationException {

        this.unprocessedCommands.offer(command);
    }

    @Scheduled(fixedRate = 33)
    private void processQueue() {

        Deque<Command> queue = new LinkedList<>(this.unprocessedCommands);
        this.unprocessedCommands.clear();

        for (Command command : queue) {

            processCommand(command);
        }
    }

    private void processCommand(Command command) {

        throw new UnsupportedOperationException();
    }
}
