package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.messaging.api.StompController;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.api.StompMessageFactory;
import com.roboter5123.backend.service.model.StompMessage;
import com.roboter5123.backend.service.model.exception.NoSuchSessionException;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @PostMapping("/sessions")
    @ResponseBody
    public String createSession(@RequestBody final GameMode gameMode) throws TooManySessionsException {

        return this.gameService.createSession(gameMode);

    }

    @Override
    @SubscribeMapping("/topic/game/{sessionCode}")
    public StompMessage joinSession(@Header final String playerName, @DestinationVariable final String sessionCode) {

        final Map<String,Object> reply;

        try {

            reply = gameService.joinGame(sessionCode, playerName);

        } catch (NoSuchSessionException e) {

            return messageFactory.getMessage(e);
        }


        return messageFactory.getMessage(reply);
    }
}
