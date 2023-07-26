package com.pixelthump.seshservice.sesh.implementation.quizxel;
import com.pixelthump.seshservice.messaging.api.MessageBroadcaster;
import com.pixelthump.seshservice.messaging.model.Command;
import com.pixelthump.seshservice.messaging.model.action.Action;
import com.pixelthump.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.pixelthump.seshservice.sesh.implementation.quizxel.model.question.QuizxelQuestion;
import com.pixelthump.seshservice.sesh.implementation.quizxel.model.state.QuizxelControllerMainStageState;
import com.pixelthump.seshservice.sesh.implementation.quizxel.model.state.QuizxelHostMainStageState;
import com.pixelthump.seshservice.sesh.api.Player;
import com.pixelthump.seshservice.sesh.model.SeshStage;
import com.pixelthump.seshservice.sesh.model.SeshType;
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
    private boolean showAnswer;

    public QuizxelSesh(MessageBroadcaster broadcaster) {

        super(broadcaster, SeshType.QUIZXEL, new QuizxelPlayerManager(MAXPLAYERS), MAXPLAYERS);
        this.questionProvider = new QuizxelQuestionProvider();
    }

    protected void startMainStage() {

        this.currentStage = SeshStage.MAIN;
        this.currentQuestion = questionProvider.getCurrentQuestion();
        broadcastState();
    }

    protected QuizxelHostMainStageState getHostMainStageState() {

        QuizxelHostMainStageState state = new QuizxelHostMainStageState();
        state.setPlayers(this.playerManager.getPlayers());
        state.setSeshCode(this.getSeshCode());
        state.setCurrentStage(this.currentStage);
        state.setCurrentQuestion(this.currentQuestion);
        state.setBuzzedPlayerId(this.buzzedPlayerId);
        state.setShowQuestion(this.showQuestion);
        state.setShowAnswer(this.showAnswer);

        return state;
    }

    protected QuizxelControllerMainStageState getControllerMainStageState() {

        QuizxelControllerMainStageState state = new QuizxelControllerMainStageState();
        state.setPlayers(this.playerManager.getPlayers());
        state.setSeshCode(this.getSeshCode());
        state.setCurrentStage(this.currentStage);

        return state;
    }

    protected void broadcastMainStageState() {

        broadcastToHost(getHostState());
        broadcastToAllControllers(getHostState());
    }

    protected void processMainCommand(Command command) {

        Action<?> action = command.getAction();
        String actionType = action.getType();

        switch (actionType) {

            case "nextQuestion" -> handleNextQuestionCommand(command);
            case "showQuestion" -> handleShowQuestionCommand(command);
            case "showAnswer" -> handleShowAnswerCommand(command);
            case "buzzer" -> handleBuzzerCommand(command);
            case "freeBuzzer" -> handleFreeBuzzerCommand(command);
            default -> log.error("QuizxelSesh: Got a command without a valid Action type. Action={}", action);
        }
    }

    private void handleShowAnswerCommand(Command command) {

        Action<Boolean> action = (Action<Boolean>) command.getAction();
        String executerId = command.getPlayerId();

        if (!this.playerManager.isVIP(executerId)) return;

        this.showAnswer = action.getBody();
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
        Action<String> action = (Action<String>) command.getAction();

        if ("next".equals(action.getBody())) this.currentQuestion = questionProvider.getNextQuestion();
        if ("prev".equals(action.getBody())) this.currentQuestion = questionProvider.getPreviousQuestion();

        this.showQuestion = false;
        this.showAnswer = false;
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
