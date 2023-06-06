package com.roboter5123.play.backend.api.implementation;
import com.roboter5123.play.backend.api.api.StompController;
import com.roboter5123.play.backend.service.api.GameService;
import com.roboter5123.play.backend.service.exception.NoSuchSessionException;
import com.roboter5123.play.messaging.api.StompMessageFactory;
import com.roboter5123.play.messaging.model.StompMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class StompControllerImpl implements StompController {

    private final GameService gameService;
    private final StompMessageFactory messageFactory;


    @Autowired
    public StompControllerImpl(final GameService gameService, final StompMessageFactory messageFactory) {

        this.gameService = gameService;
        this.messageFactory = messageFactory;
    }

    @Override
    @SubscribeMapping("/topic/game/{sessionCode}")
    public StompMessage joinSession(@Header final String playerName, @DestinationVariable final String sessionCode) {

        final Map<String, Object> reply;

        try {

            reply = gameService.joinGame(sessionCode, playerName);

        } catch (NoSuchSessionException e) {

            return messageFactory.getMessage(e);
        }

        return messageFactory.getMessage(reply);
    }
}
