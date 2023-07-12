package com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import com.roboter5123.play.backend.seshservice.sesh.api.Player;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelState;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshType;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
    protected QuizxelState getMainStageState() {

        QuizxelState state = new QuizxelState();
        state.setPlayers(this.playerManager.getPlayers());
        state.setSeshCode(this.getSeshCode());
        state.setCurrentStage(this.currentStage);
        state.setCurrentQuestion(this.currentQuestion);
        state.setBuzzeredPlayerId(this.buzzedPlayerId);
        state.setShowQuestion(this.showQuestion);

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
            case "freeBuzzer" -> handleFreeBuzzerCommand(command);
            default -> log.error("QuizxelSesh: Got a command without a valid Action type. Action={}", action);
        }
    }

    private void handleShowQuestionCommand(Command command) {

        Action<Boolean> action = (Action<Boolean>) command.getAction();
        String executerId = command.getPlayerId();

        if (!this.playerManager.isVIP(executerId)) return;

        this.showQuestion = action.getBody();
    }

    private void handleBuzzerCommand(Command command) {

        String executerId = command.getPlayerId();

        if (isBuzzed() && !this.playerManager.isVIP(executerId)) return;

        if (this.playerManager.isVIP(executerId)) {

            unlockBuzzer();

        } else {

            buzzer(executerId);
        }
    }

    private void handleFreeBuzzerCommand(Command command) {

        Action<Boolean> action = (Action<Boolean>) command.getAction();
        String executerId = command.getPlayerId();

        if (!this.playerManager.isVIP(executerId)) return;
        if (!isBuzzed()) return;

        if (action.getBody() == null) {

            unlockBuzzer();

        } else if (Boolean.TRUE.equals(action.getBody())) {

            awardPointsToPlayer(this.buzzedPlayerId);
            unlockBuzzer();

        } else if (Boolean.FALSE.equals(action.getBody())) {

            awardPointsToAllOtherPlayers(this.buzzedPlayerId);
            unlockBuzzer();
        }
    }

    private void handleNextQuestionCommand(Command command) {

        if (!this.playerManager.isVIP(command.getPlayerId())) return;
        this.currentQuestion = questionProvider.getNextQuestion();
        this.showQuestion = false;
    }

    private void awardPointsToPlayer(String buzzedPlayerId) {

        this.playerManager.getPlayer(buzzedPlayerId).addPoints(this.playerManager.getPlayerCount() - 2);
    }

    private void awardPointsToAllOtherPlayers(String buzzedPlayerId) {

        for (Player player : this.playerManager.getPlayers()) {

            if (!player.getPlayerId().equals(buzzedPlayerId)) {

                player.addPoints(1L);
            }
        }
    }

    private boolean isBuzzed() {

        return this.buzzered || (this.buzzedPlayerId != null);
    }

    private void unlockBuzzer() {

        this.buzzered = false;
        this.buzzedPlayerId = null;
    }

    private void buzzer(String playerId) {

        this.buzzered = true;
        this.buzzedPlayerId = playerId;
    }
}
