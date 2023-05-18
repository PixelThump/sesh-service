package com.roboter5123.backend.stomp;
import com.roboter5123.backend.service.GameService;
import com.roboter5123.backend.service.model.StompMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class GameController {

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;
    Logger logger;


    @Autowired
    public GameController( GameService gameService, SimpMessagingTemplate messagingTemplate) {

        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @SubscribeMapping("/topic/game/{gameCode}")
    public StompMessage subscribe(@DestinationVariable String gameCode, @Header("playerName") String playerName) {


            Map<String, StompMessage> messages= gameService.joinGame(gameCode, playerName);

            if (messages.containsKey("error")){

                return messages.get("error");
            }

            messagingTemplate.convertAndSend("/topic/game/"+gameCode, messages.get("broadcast"));
            return messages.get("reply");


    }
}
