package com.roboter5123.backend.messaging;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.messaging.model.StompMessageFactory;
import com.roboter5123.backend.service.GameService;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class MessagingControllerImpl implements MessagingController{

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
    @SubscribeMapping("/topic/game/{gameCode}")
    public StompMessage joinSession(String playerName, String sessionCode) {

        Map<String, Object> payloads;

        try {

            payloads = gameService.joinGame(sessionCode, playerName);

        }catch (NoSuchSessionException e){

            return messageFactory.getMessage(e);
        }

        StompMessage broadcast = messageFactory.getMessage(payloads.get("braoadcast"));
        broadcast("/topic/game/"+sessionCode, broadcast);

        return messageFactory.getMessage(payloads.get("reply"));
    }

    @Override
    public void broadcast(String sessionCode, Object payload) {

        StompMessage message = messageFactory.getMessage(payload);
        this.broadcaster.broadcast(sessionCode,message);
    }

}
