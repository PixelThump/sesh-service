package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
@Log4j2
public class QuizxelSesh extends AbstractSeshBaseClass {

    private static final Integer MAXPLAYERS = 5;
    private final QuizxelQuestionProvider questionProvider;
    private QuizxelQuestion currentQuestion;
    private boolean buzzered;
    private String buzzedPlayerId;
    private boolean showQuestion;

    public QuizxelSesh(MessageBroadcaster broadcaster) {

        super(broadcaster, SeshType.QUIZXEL, new QuizxelPlayerManager(MAXPLAYERS), MAXPLAYERS);
        this.questionProvider = new QuizxelQuestionProvider();
    }

    @Override
    protected Map<String, Object> getMainStageState() {

        Map<String, Object> state = getLobbyState();
        state.put("currentQuestion", this.currentQuestion);
        state.put("buzzedPlayerId", this.buzzedPlayerId);
        state.put("showQuestion", this.showQuestion);

        return state;
    }

    @Override
    protected void startMainStage() {

        this.currentStage = SeshStage.MAIN;
        this.currentQuestion = questionProvider.getNextQuestion();
        this.broadcastToAll(getState());
    }

    @Override
    protected void processMainCommand(Command command) {

        Action<?> action = command.getAction();
        String actionType = action.getType();

        switch (actionType) {
            case "nextQuestion" -> handleNextQuestionCommand(command);
            case "showQuestion" -> handleShowQuestionCommand(command);
            case "buzzer" -> handleBuzzerCommand(command);
            default -> log.error("QuizxelSesh: Got a command without a valid Action type. Action={}", action);
        }
    }

    private void handleShowQuestionCommand(Command command) {

        Action<Boolean> action = command.getAction();
        String executerId = command.getPlayerId();

        if (!this.playerManager.isVIP(executerId)) return;

        this.showQuestion = action.getBody();
    }

    private void handleBuzzerCommand(Command command) {

        Action<Boolean> action = command.getAction();
        String executerId = command.getPlayerId();

        if (this.buzzered && !this.playerManager.isVIP(executerId)) return;

        if (this.playerManager.isVIP(executerId)) {

            this.buzzered = action.getBody();

        } else {

            this.buzzered = true;
            this.buzzedPlayerId = executerId;
        }
    }

    private void handleNextQuestionCommand(Command command) {

        if (!this.playerManager.isVIP(command.getPlayerId())) return;
        this.currentQuestion = questionProvider.getNextQuestion();
        this.showQuestion = false;
    }
}
