package com.roboter5123.play.backend.seshservice.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Action;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshCurrentlyNotJoinableException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelSesh;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelJoinAction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class QuizxelSeshTest {

    private static final String SESHCODE = "ABCD";
    QuizxelSesh sesh;
    MessageBroadcaster broadcaster;

    @BeforeEach
    void setUp() {

        this.broadcaster = mock(MessageBroadcaster.class);
        sesh = new QuizxelSesh(SESHCODE, broadcaster);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void joinSeshAsController_should_throw_player_already_joined_exception() {

        final String playerName = "roboter5123";
        this.sesh.joinSeshAsHost();
        this.sesh.joinSeshAsController(playerName);
        PlayerAlreadyJoinedException exception = assertThrows(PlayerAlreadyJoinedException.class, () -> this.sesh.joinSeshAsController(playerName));
        String expectedMessage = "Player with name " + playerName + " has already joined the Sesh";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void joinSeshAsController_should_throw_sesh_is_full_exception() {

        final String playerName1 = "roboter5123";
        final String playerName2 = "roboter51234";
        final String playerName3 = "roboter512345";
        final String playerName4 = "roboter5123456";
        final String playerName5 = "roboter51234567";
        final String playerName6 = "roboter512345678";
        this.sesh.joinSeshAsHost();
        this.sesh.joinSeshAsController(playerName1);
        this.sesh.joinSeshAsController(playerName2);
        this.sesh.joinSeshAsController(playerName3);
        this.sesh.joinSeshAsController(playerName4);
        this.sesh.joinSeshAsController(playerName5);
        SeshIsFullException exception = assertThrows(SeshIsFullException.class, () -> this.sesh.joinSeshAsController(playerName6));
        assertTrue(exception.getMessage().contains("A maximum of "));
        assertTrue(exception.getMessage().contains(" is allowed to join this Sesh."));
    }

    @Test
    void joinSeshAsController_should_broadcast_join_message_on_successful_join() {

        final String playerName = "roboter5123";
        this.sesh.joinSeshAsHost();
        this.sesh.joinSeshAsController(playerName);
        ArgumentCaptor<Command> argumentCaptor = ArgumentCaptor.forClass(Command.class);
        verify(broadcaster).broadcastSeshUpdate(eq(SESHCODE), argumentCaptor.capture());

        Action sentAction = argumentCaptor.getValue().getAction();
        assertInstanceOf(QuizxelJoinAction.class, sentAction);

        QuizxelJoinAction quizxelJoinAction = (QuizxelJoinAction) sentAction;
        assertEquals(playerName, quizxelJoinAction.getJoiningPlayer());
    }

    @Test
    void joinSeshAsController_should_throw_Sesh_currently_not_joinable_exception() {

        final String playerName = "roboter5123";
        SeshCurrentlyNotJoinableException exception = assertThrows(SeshCurrentlyNotJoinableException.class, () -> this.sesh.joinSeshAsController(playerName));
        String expectedErrorMessage = "Host hasn't connected yet. Try again later.";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void joinSeshAsHost_should_throw_player_already_joined_exception() {

        this.sesh.joinSeshAsHost();
        PlayerAlreadyJoinedException exception = assertThrows(PlayerAlreadyJoinedException.class, () -> this.sesh.joinSeshAsHost());
        String expectedMessage = "Host has already joined this sesh";
        assertEquals(expectedMessage, exception.getMessage());
    }

//    @Test
//    void joinSesh_should_add_player_to_players() throws NoSuchFieldException, IllegalAccessException {
//
//        final String playerName = "roboter5123";
//        this.sesh.joinSesh(playerName);
//        Field playersField = this.sesh.getClass().getDeclaredField("players");
//        playersField.setAccessible(true);
//        Object playersObject = playersField.get(this.sesh);
//
//        List<QuizxelPlayer> playersList = (List<QuizxelPlayer>) playersObject;
//        assertEquals(playerName, playersList.get(0).getPlayerName());
//
//    }

//    @Test
//    void joinSesh_with_host_should_set_host_joined_to_true() throws NoSuchFieldException, IllegalAccessException {
//
//        final String playerName = "host";
//        this.sesh.joinSesh(playerName);
//        Field hostJoinedField = this.sesh.getClass().getDeclaredField("hostJoined");
//        hostJoinedField.setAccessible(true);
//        boolean hostJoined = hostJoinedField.getBoolean(this.sesh);
//        assertTrue(hostJoined);
//    }
}