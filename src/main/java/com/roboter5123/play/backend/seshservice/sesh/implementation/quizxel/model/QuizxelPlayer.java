package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model;
import lombok.Data;

@Data
public class QuizxelPlayer {

    private final String playerName;
    private Boolean isVip;

    public QuizxelPlayer(String playerName) {

        this.playerName = playerName;
        this.isVip = false;
    }
}
