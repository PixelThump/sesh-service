package com.roboter5123.backend.stomp;
import com.roboter5123.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameController {

    private final GameService gameService;


    @Autowired
    public GameController(GameService gameService) {

        this.gameService = gameService;
    }

    @SubscribeMapping("/topic/game/{gameCode}")
    public List<String> subscribe(@DestinationVariable String gameCode, @Header("playerName") String playerName){

        return gameService.joinGame(gameCode, playerName);
    }
}
