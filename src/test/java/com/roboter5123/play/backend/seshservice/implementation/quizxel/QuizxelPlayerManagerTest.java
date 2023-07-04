package com.roboter5123.play.backend.seshservice.implementation.quizxel;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.QuizxelPlayerManager;
import com.roboter5123.play.backend.seshservice.sesh.implementation.quizxel.model.QuizxelPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuizxelPlayerManagerTest {

    QuizxelPlayerManager playerManager;
    private String socketId;

    @BeforeEach
    void setUp() {

        playerManager = new QuizxelPlayerManager(5);
        socketId = "asda415asd";
    }

    @Test
    void addPlayerToSesh_should_add_player_to_players(){

        final String playerName = "roboter5123";
        this.playerManager.joinAsPlayer(playerName, this.socketId);


        List<QuizxelPlayer> playersList = this.playerManager.getPlayers();
        assertEquals(playerName, playersList.get(0).getPlayerName());
        assertEquals(this.socketId, playersList.get(0).getPlayerId());
    }

    @Test
    void joinSesh_with_host_should_set_host_joined_to_true() throws NoSuchFieldException, IllegalAccessException {

        this.playerManager.joinAsHost(this.socketId);
        Field hostIdField = this.playerManager.getClass().getDeclaredField("hostId");
        hostIdField.setAccessible(true);
        Object hostIdObject = hostIdField.get(this.playerManager);
        String hostId = (String) hostIdObject;
        assertEquals(this.socketId, hostId);
    }
}