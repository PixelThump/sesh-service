package com.roboter5123.backend.messaging;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.messaging.model.StompMessageFactory;
import com.roboter5123.backend.service.GameService;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.JoinPayloads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingControllerImpl implements MessagingController {

    private final GameService gameService;
    private final MessageBroadcaster broadcaster;
    Logger logger;
    StompMessageFactory messageFactory;

    @Autowired
    public MessagingControllerImpl(GameService gameService, MessageBroadcaster broadcaster, StompMessageFactory messageFactory) {

        this.gameService = gameService;
        this.broadcaster = broadcaster;
        this.messageFactory = messageFactory;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public StompMessage createSession() {

//        TODO:Implement
        return null;
    }

    @Override
    @SubscribeMapping("/topic/game/{sessionCode}")
    public StompMessage joinSession(@Header String playerName, @DestinationVariable String sessionCode) {

        JoinPayloads payloads;

        try {

            payloads = gameService.joinGame(sessionCode, playerName);

        } catch (NoSuchSessionException e) {

            return messageFactory.getMessage(e);
        }

        Command broadcast = payloads.getBroadcast();
        broadcast(sessionCode, broadcast);

        return messageFactory.getMessage(payloads.getReply());
    }

    @Override
    public void broadcast(String sessionCode, Object payload) {

        this.broadcaster.broadcastGameUpdate(sessionCode, payload);
    }

}
