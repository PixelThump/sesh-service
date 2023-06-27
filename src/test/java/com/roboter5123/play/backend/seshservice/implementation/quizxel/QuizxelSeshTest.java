package com.roboter5123.play.backend.seshservice.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.seshservice.messaging.model.Action;
import com.roboter5123.play.backend.seshservice.messaging.model.Command;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelSesh;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelJoinAction;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelPlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.util.List;

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
    void joinSesh_should_throw_player_already_joined_exception() {

        final String playerName = "roboter5123";
        this.sesh.joinSesh(playerName);
        PlayerAlreadyJoinedException exception = assertThrows(PlayerAlreadyJoinedException.class, () -> this.sesh.joinSesh(playerName));
        String expectedMessage = "Player with name " + playerName + " has already joined the Sesh";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void joinSesh_should_throw_sesh_is_full_exception() {

        final String playerName1 = "roboter5123";
        final String playerName2 = "roboter51234";
        final String playerName3 = "roboter512345";
        final String playerName4 = "roboter5123456";
        final String playerName5 = "roboter51234567";
        final String playerName6 = "roboter512345678";
        this.sesh.joinSesh(playerName1);
        this.sesh.joinSesh(playerName2);
        this.sesh.joinSesh(playerName3);
        this.sesh.joinSesh(playerName4);
        this.sesh.joinSesh(playerName5);
        SeshIsFullException exception = assertThrows(SeshIsFullException.class, () -> this.sesh.joinSesh(playerName6));
        assertTrue(exception.getMessage().contains("A maximum of "));
        assertTrue(exception.getMessage().contains(" is allowed to join this Sesh."));

    }

    @Test
    void joinSesh_should_broadcast_join_message_on_successful_join() {

        final String playerName = "roboter5123";
        this.sesh.joinSesh(playerName);
        ArgumentCaptor<Command> argumentCaptor = ArgumentCaptor.forClass(Command.class);
        verify(broadcaster).broadcastSeshUpdate(eq(SESHCODE), argumentCaptor.capture());

        Action sentAction = argumentCaptor.getValue().getAction();
        assertInstanceOf(QuizxelJoinAction.class, sentAction);

        QuizxelJoinAction quizxelJoinAction = (QuizxelJoinAction) sentAction;
        assertEquals(playerName, quizxelJoinAction.getJoiningPlayer());
    }

    @Test
    void joinSesh_should_add_player_to_players() throws NoSuchFieldException, IllegalAccessException {

        final String playerName = "roboter5123";
        this.sesh.joinSesh(playerName);
        Field playersField = this.sesh.getClass().getDeclaredField("players");
        playersField.setAccessible(true);
        Object playersObject = playersField.get(this.sesh);

        List<QuizxelPlayer> playersList = (List<QuizxelPlayer>) playersObject;
        assertEquals(playerName, playersList.get(0).getPlayerName());

    }

    @Test
    void joinSesh_with_host_should_set_host_joined_to_true() throws NoSuchFieldException, IllegalAccessException {

        final String playerName = "host";
        this.sesh.joinSesh(playerName);
        Field hostJoinedField = this.sesh.getClass().getDeclaredField("hostJoined");
        hostJoinedField.setAccessible(true);
        boolean hostJoined = hostJoinedField.getBoolean(this.sesh);
        assertTrue(hostJoined);
    }
}