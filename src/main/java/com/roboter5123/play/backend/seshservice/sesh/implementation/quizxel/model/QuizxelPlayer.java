package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model;
import com.roboter5123.play.backend.seshservice.sesh.model.Player;
import lombok.Data;

@Data
public class QuizxelPlayer implements Player {

    private final String playerName;
    private Boolean vip;
    private String playerId;
    private Long points;

    public QuizxelPlayer(String playerName, String playerid) {

        this.playerName = playerName;
        this.vip = false;
        this.playerId = playerid;
        this.points = 0L;
    }

    @Override
    public Long addPoints(Long points) {

        this.points += points;
        return this.points;
    }
}
