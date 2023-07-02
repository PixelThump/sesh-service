package com.roboter5123.play.backend.seshservice.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.*;
import com.roboter5123.play.backend.seshservice.messaging.model.action.Action;
import com.roboter5123.play.backend.seshservice.messaging.model.action.BasicAction;
import com.roboter5123.play.backend.seshservice.messaging.model.action.MakeVIPAction;
import com.roboter5123.play.backend.seshservice.messaging.model.action.StartSeshAction;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerNotInSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.AbstractSeshBaseClass;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelSesh;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelJoinAction;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelPlayer;
import com.roboter5123.play.backend.seshservice.sesh.model.SeshStage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class QuizxelSeshTest {

    private static final String SESHCODE = "ABCD";
    QuizxelSesh sesh;
    MessageBroadcaster broadcaster;
    private String playerName;

    @BeforeEach
    void setUp() {

        this.broadcaster = mock(MessageBroadcaster.class);
        sesh = new QuizxelSesh(SESHCODE, broadcaster);
        this.playerName = "roboter5123";
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void joinSeshAsHost_should_throw_player_already_joined_exception() {

        this.sesh.joinSeshAsHost();
        PlayerAlreadyJoinedException exception = assertThrows(PlayerAlreadyJoinedException.class, () -> this.sesh.joinSeshAsHost());
        String expectedMessage = "Host has already joined this sesh";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void joinSeshAsHost_should_return_state() {

        Map<String, Object> expected = new HashMap<>();
        expected.put("players", new ArrayList<QuizxelPlayer>());
        expected.put("maxPlayers", 5);
        expected.put("currentStage", SeshStage.LOBBY);
        Map<String, Object> result = this.sesh.joinSeshAsHost();
        assertEquals(expected, result);
    }

    @Test
    void joinSeshAsController_should_throw_player_already_joined_exception() {

        this.sesh.joinSeshAsHost();
        this.sesh.joinSeshAsController(this.playerName);
        PlayerAlreadyJoinedException exception = assertThrows(PlayerAlreadyJoinedException.class, () -> this.sesh.joinSeshAsController(this.playerName));
        String expectedMessage = "Player with name " + this.playerName + " has already joined the Sesh";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void joinSeshAsController_should_throw_sesh_is_full_exception() {

        this.sesh.joinSeshAsHost();

        for (int i = 0; i < 5; i++) {

            this.sesh.joinSeshAsController(this.playerName + i);
        }

        SeshIsFullException exception = assertThrows(SeshIsFullException.class, () -> this.sesh.joinSeshAsController(this.playerName));
        assertTrue(exception.getMessage().contains("A maximum of "));
        assertTrue(exception.getMessage().contains(" is allowed to join this Sesh."));
    }

    @Test
    void joinSeshAsController_should_broadcast_join_message_on_successful_join() {

        this.sesh.joinSeshAsHost();
        this.sesh.joinSeshAsController(this.playerName);
        ArgumentCaptor<Command> argumentCaptor = ArgumentCaptor.forClass(Command.class);
        verify(this.broadcaster).broadcastSeshUpdate(eq(SESHCODE), argumentCaptor.capture());

        Action sentAction = argumentCaptor.getValue().getAction();
        assertInstanceOf(QuizxelJoinAction.class, sentAction);

        QuizxelJoinAction quizxelJoinAction = (QuizxelJoinAction) sentAction;
        assertEquals(this.playerName, quizxelJoinAction.getJoiningPlayer());
    }

    @Test
    void joinSeshAsController_should_throw_Sesh_currently_not_joinable_exception() {

        SeshCurrentlyNotJoinableException exception = assertThrows(SeshCurrentlyNotJoinableException.class, () -> this.sesh.joinSeshAsController(this.playerName));
        String expectedErrorMessage = "Host hasn't connected yet. Try again later.";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void addCommand_should_throw_Player_not_in_sesh_exception() {

        Command command = new Command(this.playerName, new QuizxelJoinAction(this.playerName));
        PlayerNotInSeshException exception = assertThrows(PlayerNotInSeshException.class, () -> this.sesh.addCommand(command));
        String expectedErrorMessage = this.playerName + " hasn't joined the sesh.";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void addCommand_should_add_command_to_unproccesssedCommands() throws NoSuchFieldException, IllegalAccessException {

        this.sesh.joinSeshAsHost();
        this.sesh.joinSeshAsController(this.playerName);

        Command command = new Command(this.playerName, new QuizxelJoinAction(this.playerName));
        this.sesh.addCommand(command);

        Field queueField = AbstractSeshBaseClass.class.getDeclaredField("unprocessedCommands");
        queueField.setAccessible(true);
        Object queueObject = queueField.get(this.sesh);
        Deque<Command> queue = (Deque<Command>) queueObject;
        assertTrue(queue.contains(command));
    }

    @Test
    void processQueue_should_call_broadcastSeshUpdate() {

        this.sesh.joinSeshAsHost();
        Map<String, Object> state = this.sesh.joinSeshAsController(this.playerName);
        this.sesh.addCommand(new Command(this.playerName, new BasicAction()));
        this.sesh.processQueue();
        verify(broadcaster).broadcastSeshUpdate(sesh.getSeshCode(), state);
    }

    @Test
    void processQueue_should_advance_current_stage_to_main() {

        this.sesh.joinSeshAsHost();
        this.sesh.joinSeshAsController(this.playerName);
        this.sesh.addCommand(new Command(this.playerName, new MakeVIPAction(this.playerName, true)));
        this.sesh.addCommand(new Command(this.playerName, new StartSeshAction(true)));
        this.sesh.processQueue();
        assertEquals(SeshStage.MAIN, this.sesh.getCurrentStage());
    }
}