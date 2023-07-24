package com.roboter5123.play.backend.seshservice.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerNotInSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelSesh;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.state.QuizxelControllerMainStageState;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.state.QuizxelHostMainStageState;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import com.roboter5123.play.backend.seshservice.sesh.model.state.AbstractSeshState;
import com.roboter5123.play.backend.seshservice.sesh.model.state.HostLobbyState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Deque;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
class QuizxelSeshTest {

    private static final String SESHCODE = "ABCD";
    QuizxelSesh sesh;
    MessageBroadcaster broadcaster;
    private String playerName;
    private String socketId;

    @BeforeEach
    void setUp() {

        this.broadcaster = mock(MessageBroadcaster.class);
        sesh = new QuizxelSesh(broadcaster);
        sesh.setSeshCode(SESHCODE);
        sesh.startSesh();
        this.playerName = "roboter5123";
        socketId = "asda63a+d";
    }

    @Test
    void joinSeshAsHost_should_throw_player_already_joined_exception() {

        this.sesh.joinSeshAsHost(this.socketId);
        PlayerAlreadyJoinedException exception = assertThrows(PlayerAlreadyJoinedException.class, () -> this.sesh.joinSeshAsHost(socketId));
        String expectedMessage = "Host has already joined this sesh";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void joinSeshAsHost_should_return_state() {

        HostLobbyState expected = new HostLobbyState();
        expected.setPlayers(new ArrayList<>());
        expected.setCurrentStage(SeshStage.LOBBY);
        expected.setSeshCode("ABCD");
        expected.setMaxPlayers(5);
        AbstractSeshState result = this.sesh.joinSeshAsHost(socketId);
        assertEquals(expected, result);
    }

    @Test
    void joinSeshAsController_should_throw_player_already_joined_exception() {

        setupPlayer();
        PlayerAlreadyJoinedException exception = assertThrows(PlayerAlreadyJoinedException.class, () -> this.sesh.joinSeshAsController(playerName, socketId + 1));
        String expectedMessage = "Player with name " + this.playerName + " has already joined the Sesh";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void joinSeshAsController_should_throw_sesh_is_full_exception() {

        this.sesh.joinSeshAsHost(socketId);

        for (int i = 0; i < 5; i++) {

            this.sesh.joinSeshAsController(this.playerName + i, socketId + i);
        }

        SeshIsFullException exception = assertThrows(SeshIsFullException.class, () -> this.sesh.joinSeshAsController(this.playerName, socketId + -1));
        assertTrue(exception.getMessage().contains("A maximum of "));
        assertTrue(exception.getMessage().contains(" is allowed to join this Sesh."));
    }

    @Test
    void joinSeshAsController_should_throw_Sesh_currently_not_joinable_exception() {

        SeshCurrentlyNotJoinableException exception = assertThrows(SeshCurrentlyNotJoinableException.class, () -> this.sesh.joinSeshAsController(this.playerName, this.socketId));
        String expectedErrorMessage = "Host hasn't connected yet. Try again later.";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void addCommand_should_throw_Player_not_in_sesh_exception() {

        Command command = new Command(this.playerName, new Action<>("join", this.playerName));
        PlayerNotInSeshException exception = assertThrows(PlayerNotInSeshException.class, () -> this.sesh.addCommand(command));
        String expectedErrorMessage = this.playerName + " hasn't joined the sesh.";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void addCommand_should_add_command_to_unproccesssedCommands() throws NoSuchFieldException, IllegalAccessException {

        String playerId = setupPlayer();

        Command command = new Command(playerId, new Action<>("join", this.playerName));
        this.sesh.addCommand(command);

        Field queueField = AbstractSeshBaseClass.class.getDeclaredField("unprocessedCommands");
        queueField.setAccessible(true);
        Object queueObject = queueField.get(this.sesh);
        //noinspection unchecked
        Deque<Command> queue = (Deque<Command>) queueObject;
        assertTrue(queue.contains(command));
    }

    private String setupPlayer() {

        this.sesh.joinSeshAsHost(socketId);
        AbstractSeshState state = this.sesh.joinSeshAsController(this.playerName, socketId + 1);
        return state.getPlayers().get(0).getPlayerId();
    }

    @Test
    void processQueue_should_call_broadcastSeshUpdate() {

        this.sesh.joinSeshAsHost(socketId);
        AbstractSeshState state = this.sesh.joinSeshAsController(this.playerName, socketId + 1);
        String playerId = sesh.getHostState().getPlayers().get(0).getPlayerId();
        this.sesh.addCommand(new Command(playerId, new Action<>("command", "any")));
        this.sesh.processQueue();
        verify(broadcaster).broadcastSeshUpdateToControllers(sesh.getSeshCode(), state);
    }

    @Test
    void processQueue_should_advance_current_stage_to_main() {

        String playerId = setupPlayer();
        this.sesh.addCommand(new Command(playerId, new Action<>("makeVip", playerId)));
        this.sesh.addCommand(new Command(playerId, new Action<>("startSesh", "whatevs")));
        this.sesh.processQueue();
        assertEquals(SeshStage.MAIN, this.sesh.getCurrentStage());
        assertEquals(QuizxelHostMainStageState.class, this.sesh.getHostState().getClass());
        assertEquals(QuizxelControllerMainStageState.class, this.sesh.getControllerState().getClass());
    }

    @Test
    void processQueue_should_handle_commands_show_question_correctly() {

        String playerId = setupPlayer();
        this.sesh.addCommand(new Command(playerId, new Action<>("makeVip", playerId)));
        this.sesh.addCommand(new Command(playerId, new Action<>("startSesh", "whatevs")));

        this.sesh.addCommand(new Command(playerId, new Action<>("showQuestion", true)));
        this.sesh.processQueue();
        QuizxelHostMainStageState state = (QuizxelHostMainStageState) this.sesh.getHostState();
        assertTrue(state.isShowQuestion());

        this.sesh.addCommand(new Command(playerId, new Action<>("showQuestion", false)));
        this.sesh.processQueue();
        state = (QuizxelHostMainStageState) this.sesh.getHostState();
        assertFalse(state.isShowQuestion());
    }

    @Test
    void processQueue_should_handle_commands_show_answer_correctly() {

        String playerId = setupPlayer();

        this.sesh.addCommand(new Command(playerId, new Action<>("makeVip", playerId)));
        this.sesh.addCommand(new Command(playerId, new Action<>("startSesh", "whatevs")));

        this.sesh.addCommand(new Command(playerId, new Action<>("showAnswer", true)));
        this.sesh.processQueue();
        QuizxelHostMainStageState state = (QuizxelHostMainStageState) this.sesh.getHostState();
        assertTrue(state.isShowAnswer());

        this.sesh.addCommand(new Command(playerId, new Action<>("showAnswer", false)));
        this.sesh.processQueue();
        state = (QuizxelHostMainStageState) this.sesh.getHostState();
        assertFalse(state.isShowAnswer());
    }

    @Test
    void processQueue_should_handle_commands_free_buzzer_correctly() {

        String vipId = setupPlayer();
        this.sesh.joinSeshAsController("player", socketId + "player");
        String playerId = socketId + "player";

        this.sesh.addCommand(new Command(vipId, new Action<>("makeVip", vipId)));
        this.sesh.addCommand(new Command(vipId, new Action<>("startSesh", "whatevs")));
        this.sesh.addCommand(new Command(playerId, new Action<>("buzzer", true)));
        this.sesh.processQueue();
        QuizxelHostMainStageState state = (QuizxelHostMainStageState) this.sesh.getHostState();
        assertNotNull(state.getBuzzedPlayerId());

        this.sesh.addCommand(new Command(vipId, new Action<>("freeBuzzer", true)));
        this.sesh.processQueue();
        state = (QuizxelHostMainStageState) this.sesh.getHostState();
        assertNull(state.getBuzzedPlayerId());

        this.sesh.addCommand(new Command(playerId, new Action<>("buzzer", true)));
        this.sesh.addCommand(new Command(vipId, new Action<>("freeBuzzer", null)));
        this.sesh.processQueue();
        state = (QuizxelHostMainStageState) this.sesh.getHostState();
        assertNull(state.getBuzzedPlayerId());

        this.sesh.addCommand(new Command(playerId, new Action<>("buzzer", true)));
        this.sesh.addCommand(new Command(vipId, new Action<>("freeBuzzer", false)));
        this.sesh.processQueue();
        state = (QuizxelHostMainStageState) this.sesh.getHostState();
        assertNull(state.getBuzzedPlayerId());
    }
}