package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import lombok.Data;

@Data
public class QuizxelJoinAction implements Action {

    private final String joiningPlayer;

    public QuizxelJoinAction(String playerName) {

        this.joiningPlayer = playerName;
    }
}
