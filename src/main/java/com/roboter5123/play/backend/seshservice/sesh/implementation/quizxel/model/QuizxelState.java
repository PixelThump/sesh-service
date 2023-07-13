package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;
import com.roboter5123.play.backend.seshservice.sesh.model.AbstractSeshState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizxelState extends AbstractSeshState {

    private QuizxelQuestion currentQuestion;
    private String buzzedPlayerId;
    private boolean showQuestion;
    private boolean showAnswer;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof QuizxelState that)) return false;
        if (!super.equals(o)) return false;
        return showQuestion == that.showQuestion && Objects.equals(currentQuestion, that.currentQuestion) && Objects.equals(buzzedPlayerId, that.buzzedPlayerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), currentQuestion, buzzedPlayerId, showQuestion);
    }
}
