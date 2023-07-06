package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.action.NextQuestionAction;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class QuizxelSesh extends AbstractSeshBaseClass {

    private static final Integer MAXPLAYERS = 5;
    private final QuizxelQuestionProvider questionProvider;
    private QuizxelQuestion currentQuestion;

    public QuizxelSesh(MessageBroadcaster broadcaster) {

        super(broadcaster, SeshType.QUIZXEL, new QuizxelPlayerManager(MAXPLAYERS), MAXPLAYERS);
        this.questionProvider = new QuizxelQuestionProvider();
    }

    @Override
    protected Map<String, Object> getMainStageState() {

        Map<String, Object> state = new HashMap<>();
        state.put("players", this.playerManager.getPlayers());
        state.put("currentQuestion", this.currentQuestion);
        state.put("currentStage", this.currentStage);

        return state;
    }

    @Override
    protected void startMainStage() {

        this.currentStage = SeshStage.MAIN;
        this.currentQuestion = questionProvider.getNextQuestion();
    }

    @Override
    protected void processMainCommand(Command command) {

        String executerId = command.getPlayerId();
        Action action = command.getAction();

        if (action instanceof NextQuestionAction) {

            if (!this.playerManager.isVIP(executerId)) return;
            this.currentQuestion = questionProvider.getNextQuestion();
        }
    }
}
