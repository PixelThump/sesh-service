package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelJoinAction;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelPlayer;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@ToString
public class QuizxelSesh extends AbstractSeshBaseClass {

    private boolean isJoinable;
    private static final Integer MAXPLAYERS = 5;
    private final List<QuizxelPlayer> players;
    private boolean hostJoined;

    public QuizxelSesh(String seshCode, MessageBroadcaster broadcaster) {

        super(seshCode, broadcaster, SeshType.QUIZXEL);
        this.isJoinable = true;
        this.hostJoined = false;
        this.players = new ArrayList<>();
    }

    @Override
    public Map<String, Object> joinSesh(String playerName) throws PlayerAlreadyJoinedException, SeshIsFullException {

        if (isSeshFull()){

            throw new SeshIsFullException("A maximum of " + MAXPLAYERS + " is allowed to join this Sesh.");
        }

        if ( hasPlayerAlreadyJoined(playerName)) {

            throw new PlayerAlreadyJoinedException("Player with name " + playerName + " has already joined the Sesh");
        }

        if (!this.hostJoined && playerName.equals("host")){

            this.hostJoined = true;

        } else {

            this.addPlayerToSesh(playerName);
            this.isJoinable = isSeshFull();
            this.broadcastJoinCommand(playerName);
        }

        return this.getState();
    }

    private void broadcastJoinCommand(String playerName) {

        Command command = new Command("Server", new QuizxelJoinAction(playerName));
        this.broadcast(command);
    }

    private void addPlayerToSesh(String playerName) {

        this.players.add(new QuizxelPlayer(playerName));
    }

    private boolean hasPlayerAlreadyJoined(String playerName) {

        boolean playerHasJoinedAlready = this.players.stream().anyMatch(quizxelPlayer -> quizxelPlayer.getPlayerName().equals(playerName));
        playerHasJoinedAlready = playerHasJoinedAlready || (playerName.equals("Host") && this.hostJoined);
        return playerHasJoinedAlready;
    }

    private boolean isSeshFull() {

        return MAXPLAYERS <= this.players.size();
    }

    private Map<String, Object> getState() {

        Map<String, Object> state = new HashMap<>();
        state.put("players", this.players);
        state.put("maxPlayers", MAXPLAYERS);

        return state;
    }

    @Override
    public void addCommand(Command command) throws UnsupportedOperationException {

//        TODO: Implement
    }
}
