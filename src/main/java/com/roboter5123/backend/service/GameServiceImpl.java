package com.roboter5123.backend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    Map<String, List<String>> games;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameServiceImpl(SimpMessagingTemplate messagingTemplate) {

        this.games = new HashMap<>();
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public List<String> joinGame(String gameCode, String playerName) {

        List<String> game = this.games.computeIfAbsent(gameCode, k ->
                {
                    this.games.put(k, new ArrayList<>());
                    return this.games.get(k);
                }
        );

        game.add(playerName);
        this.messagingTemplate.convertAndSend("/topic/game/" + gameCode, playerName);
        return game;
    }
}
