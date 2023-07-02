package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model;
import com.roboter5123.play.backend.seshservice.sesh.model.PlayerId;
import lombok.Data;

@Data
public class QuizxelPlayer {

    private final String playerName;
    private Boolean vip;
    private PlayerId playerId;

    public QuizxelPlayer(String playerName) {

        this.playerName = playerName;
        this.vip = false;
        this.playerId = new PlayerId();
    }

    public String getPlayerId() {

        return this.playerId.getId();
    }
}
