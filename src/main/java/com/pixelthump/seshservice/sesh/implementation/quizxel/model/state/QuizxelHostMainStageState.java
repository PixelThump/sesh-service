package com.pixelthump.seshservice.sesh.implementation.quizxel.model.state;
import com.pixelthump.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;
import com.pixelthump.seshservice.sesh.model.state.AbstractSeshState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuizxelHostMainStageState extends AbstractSeshState {

    private QuizxelQuestion currentQuestion;
    private boolean showQuestion;
    private boolean showAnswer;
    private String buzzedPlayerId;
}
