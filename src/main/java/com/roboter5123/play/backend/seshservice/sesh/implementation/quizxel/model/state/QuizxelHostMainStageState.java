package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.state;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;
import com.roboter5123.play.backend.seshservice.sesh.model.state.AbstractSeshState;
import lombok.Data;

import java.util.Objects;

@Data
public class QuizxelHostMainStageState extends AbstractSeshState {

    private QuizxelQuestion currentQuestion;
    private boolean showQuestion;
    private boolean showAnswer;
    private String buzzedPlayerId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof QuizxelHostMainStageState that)) return false;
        if (!super.equals(o)) return false;
        return showQuestion == that.showQuestion && showAnswer == that.showAnswer && Objects.equals(currentQuestion, that.currentQuestion) && Objects.equals(buzzedPlayerId, that.buzzedPlayerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), currentQuestion, showQuestion, showAnswer, buzzedPlayerId);
    }
}
