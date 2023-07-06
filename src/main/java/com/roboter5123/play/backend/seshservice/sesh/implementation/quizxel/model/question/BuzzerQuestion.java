package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question;
import lombok.Data;

@Data
public class BuzzerQuestion implements QuizxelQuestion {

    private String questionText;
    private String questionAnswer;

    public BuzzerQuestion(String questionText, String questionAnswer) {

        this.questionText = questionText;
        this.questionAnswer = questionAnswer;
    }
}
