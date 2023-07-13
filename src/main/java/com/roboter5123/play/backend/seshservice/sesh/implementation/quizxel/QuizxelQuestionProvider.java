package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.BuzzerQuestion;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;

import java.util.ArrayList;
import java.util.List;

public class QuizxelQuestionProvider {

    private final List<QuizxelQuestion> questions;
    private int currentIndex;

    public QuizxelQuestionProvider() {

        List<QuizxelQuestion> localQuestions = new ArrayList<>();
        localQuestions.add(new BuzzerQuestion("Test Question Text 1", "Test Question Answer 1"));
        localQuestions.add(new BuzzerQuestion("Test Question Text 2", "Test Question Answer 2"));
        this.questions = localQuestions;
        currentIndex = 0;
    }

    public QuizxelQuestion getCurrentQuestion(){

        return questions.get(currentIndex);
    }

    public QuizxelQuestion getNextQuestion() {

        currentIndex++;
        if (currentIndex >= (questions.size())) currentIndex = 0;
        return questions.get(currentIndex);
    }
}
