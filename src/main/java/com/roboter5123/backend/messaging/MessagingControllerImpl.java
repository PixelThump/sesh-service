package com.roboter5123.backend.messaging;
import com.roboter5123.backend.game.Command;
import com.roboter5123.backend.game.GameMode;
import com.roboter5123.backend.messaging.model.StompMessage;
import com.roboter5123.backend.messaging.model.StompMessageFactory;
import com.roboter5123.backend.service.GameService;
import com.roboter5123.backend.service.exception.NoSuchSessionException;
import com.roboter5123.backend.service.exception.TooManySessionsException;
import com.roboter5123.backend.service.model.JoinPayloads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessagingControllerImpl implements MessagingController {

    private final GameService gameService;
    private final MessageBroadcaster broadcaster;
    private final StompMessageFactory messageFactory;

    @Autowired
    public MessagingControllerImpl(final GameService gameService, final MessageBroadcaster broadcaster, final StompMessageFactory messageFactory) {

        this.gameService = gameService;
        this.broadcaster = broadcaster;
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

        final JoinPayloads payloads;

        try {

            payloads = gameService.joinGame(sessionCode, playerName);

        } catch (NoSuchSessionException e) {

            return messageFactory.getMessage(e);
        }

        final Command broadcast = payloads.getBroadcast();
        broadcast(sessionCode, broadcast);

        return messageFactory.getMessage(payloads.getReply());
    }

    @Override
    public void broadcast(final String sessionCode, final Object payload) {

        this.broadcaster.broadcastGameUpdate(sessionCode, payload);
    }

}
