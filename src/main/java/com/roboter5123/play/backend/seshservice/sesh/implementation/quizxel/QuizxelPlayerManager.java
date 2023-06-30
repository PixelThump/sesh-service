package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizxelPlayerManager {

    private final Integer maxPlayers;
    @Getter
    @Setter
    private boolean isJoinable;
    private final List<QuizxelPlayer> players;
    private final Set<String> playerNames;
    private boolean hostJoined;

    public QuizxelPlayerManager(final Integer maxPlayers) {

        this.maxPlayers = maxPlayers;
        this.isJoinable = true;
        this.hostJoined = false;
        this.players = new ArrayList<>();
        this.playerNames = new HashSet<>();
    }

    public boolean addPlayerToSesh(String playerName) {

        if (hasPlayerAlreadyJoined(playerName)) return false;

        QuizxelPlayer player = new QuizxelPlayer(playerName);
        player.setIsVip(players.stream().anyMatch(QuizxelPlayer::getIsVip));
        this.players.add(player);
        this.playerNames.add(playerName);
        this.isJoinable = isSeshFull();

        return true;
    }

    public boolean hasPlayerAlreadyJoined(String playerName) {

        boolean playerHasJoinedAlready = this.playerNames.contains(playerName);
        playerHasJoinedAlready = playerHasJoinedAlready || (playerName.equals("Host") && this.hostJoined);
        return playerHasJoinedAlready;
    }

    public boolean isSeshFull() {

        return this.players.size() >= maxPlayers;
    }

    public List<QuizxelPlayer> getPlayers() {

        return this.players;
    }

    public boolean joinAsHost() {

        if (!hasHostJoined()) {

            this.hostJoined = true;
        }

        return this.hostJoined;
    }

    public boolean hasHostJoined() throws PlayerAlreadyJoinedException {

        return this.hostJoined;
    }
}
