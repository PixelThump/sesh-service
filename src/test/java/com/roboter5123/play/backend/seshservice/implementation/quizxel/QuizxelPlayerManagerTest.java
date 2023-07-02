package com.roboter5123.play.backend.seshservice.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelPlayerManager;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuizxelPlayerManagerTest {

    QuizxelPlayerManager playerManager;

    @BeforeEach
    void setUp() {

        playerManager = new QuizxelPlayerManager(5);
    }

    @Test
    void addPlayerToSesh_should_add_player_to_players(){

        final String playerName = "roboter5123";
        this.playerManager.joinAsPlayer(playerName);


        List<QuizxelPlayer> playersList = this.playerManager.getPlayers();
        assertEquals(playerName, playersList.get(0).getPlayerName());
    }

    @Test
    void joinSesh_with_host_should_set_host_joined_to_true() throws NoSuchFieldException, IllegalAccessException {

        this.playerManager.joinAsHost();
        Field hostJoinedField = this.playerManager.getClass().getDeclaredField("hostJoined");
        hostJoinedField.setAccessible(true);
        boolean hostJoined = hostJoinedField.getBoolean(this.playerManager);
        assertTrue(hostJoined);
    }
}