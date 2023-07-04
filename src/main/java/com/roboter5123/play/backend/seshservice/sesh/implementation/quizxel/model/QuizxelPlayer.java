package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model;
import lombok.Data;

@Data
public class QuizxelPlayer {

    private final String playerName;
    private Boolean vip;
    private String playerId;

    public QuizxelPlayer(String playerName, String playerid) {

        this.playerName = playerName;
        this.vip = false;
        this.playerId = playerid;
    }
}
