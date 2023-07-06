package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.BuzzerQuestion;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;

public class QuizxelQuestionProvider {

    public QuizxelQuestion getNextQuestion(){

        return new BuzzerQuestion("Test Question Text", "Test Question Answer");
    }
}
