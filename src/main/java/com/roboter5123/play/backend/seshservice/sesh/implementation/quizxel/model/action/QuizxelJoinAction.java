package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.action;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import lombok.Data;

@Data
public class QuizxelJoinAction implements Action {

    private final String targetId;

    public QuizxelJoinAction(String targetId) {

        this.targetId = targetId;
    }
}
