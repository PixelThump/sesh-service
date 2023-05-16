package com.roboter5123.backend.stomp;
import com.roboter5123.backend.service.GameService;
import com.roboter5123.backend.game.GameState;
import com.roboter5123.backend.game.Message;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.stomp.model.MessageFactory;
import com.roboter5123.backend.stomp.model.StompMessage;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;


@Controller
public class GameController {

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ModelMapper mapper;

    Logger logger;


    @Autowired
    public GameController(GameService gameService, SimpMessagingTemplate messagingTemplate, ModelMapper mapper) {

        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
        this.mapper = mapper;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @SubscribeMapping("/topic/game/{gameCode}")
    public StompMessage subscribe(@DestinationVariable String gameCode, @Header("playerName") String playerName) {

        try {

            GameState<?> gameState = gameService.joinGame(gameCode, playerName);
            this.messagingTemplate.convertAndSend("/topic/game/" + gameCode, gameState.getJoinMessage());
            Message<?> message =  gameState.getStateMessage();
            return mapper.map(message, StompMessage.class);

        } catch (NoSuchSessionException e) {

            logger.warn("Game Session with code: {} does not exist", gameCode);
            return MessageFactory.getMessage("error", "disconnect");
        }
    }
}
