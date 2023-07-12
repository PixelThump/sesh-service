package com.roboter5123.play.backend.seshservice.api.implementation;
import com.roboter5123.play.backend.seshservice.api.api.StompController;
import com.roboter5123.play.backend.seshservice.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.seshservice.messaging.model.message.CommandStompMessage;
import com.roboter5123.play.backend.seshservice.messaging.model.message.StompMessage;
import com.roboter5123.play.backend.seshservice.service.api.SeshService;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerAlreadyJoinedException;
import com.roboter5123.play.backend.seshservice.sesh.exception.PlayerNotInSeshException;
import com.roboter5123.play.backend.seshservice.sesh.exception.SeshIsFullException;
import com.roboter5123.play.backend.seshservice.sesh.model.AbstractSeshState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
public class StompControllerImpl implements StompController {

    private final SeshService seshService;
    private final StompMessageFactory messageFactory;

    @Autowired
    public StompControllerImpl(final SeshService seshService, final StompMessageFactory messageFactory) {

        this.seshService = seshService;
        this.messageFactory = messageFactory;
    }

    @Override
    @SubscribeMapping("/topic/sesh/{seshCode}/controller")
    public StompMessage joinSeshAsController(@Header final String playerName, @DestinationVariable final String seshCode, final @Header("simpSessionId") String socketId) {

        log.info("StompControllerImpl: Entering joinSeshAsController(playerName={} seshCode={}, socketId={})", playerName, seshCode, socketId);

        try {

            AbstractSeshState state = seshService.joinSeshAsController(seshCode, playerName, socketId);
            StompMessage reply = messageFactory.getMessage(state);

            log.info("StompControllerImpl: Exiting joinSeshAsController(reply={})", reply);
            return reply;

        } catch (NoSuchSeshException | PlayerAlreadyJoinedException | SeshIsFullException e) {

            StompMessage reply = messageFactory.getMessage(e);
            log.error("StompControllerImpl: Exiting joinSeshAsController(error={})", reply);
            return reply;
        }
    }

    @Override
    @SubscribeMapping("/topic/sesh/{seshCode}/host")
    public StompMessage joinSeshAsHost(@DestinationVariable final String seshCode, final @Header("simpSessionId") String socketId) {

        log.info("StompControllerImpl: Entering joinSeshAsHost(seshCode={}, socketId={})", seshCode, socketId);

        try {

            AbstractSeshState state = seshService.joinSeshAsHost(seshCode, socketId);
            StompMessage reply = messageFactory.getMessage(state);

            log.info("StompControllerImpl: Exiting joinSesh(reply={})", reply);
            return reply;

        } catch (NoSuchSeshException | PlayerAlreadyJoinedException e) {

            StompMessage reply = messageFactory.getMessage(e);
            log.error("StompControllerImpl: Exiting joinSeshAsHost(reply={})", reply);
            return reply;
        }
    }

    @Override
    @MessageMapping("/topic/sesh/{seshCode}")
    public StompMessage sendCommandToSesh(final CommandStompMessage message, @DestinationVariable final String seshCode, final @Header("simpSessionId") String socketId) {

        log.info("StompControllerImpl: Entering sendCommandToSesh(message={} seshCode={}, socketId={})", message, seshCode, socketId);

        try {

            message.getCommand().setPlayerId(socketId);
            this.seshService.sendCommandToSesh(message, seshCode);
            StompMessage reply = messageFactory.getAckMessage();

            log.info("StompControllerImpl: Exiting sendCommandToSesh(reply={})", reply);
            return reply;

        } catch (NoSuchSeshException | UnsupportedOperationException | PlayerNotInSeshException e) {

            StompMessage reply = messageFactory.getMessage(e);

            log.error("StompControllerImpl: Exiting sendCommandToSesh(reply={})", reply);
            return reply;
        }
    }
}
