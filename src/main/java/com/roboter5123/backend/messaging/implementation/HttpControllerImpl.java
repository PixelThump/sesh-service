package com.roboter5123.backend.messaging.implementation;
import com.roboter5123.backend.game.api.GameMode;
import com.roboter5123.backend.messaging.api.HttpController;
import com.roboter5123.backend.service.api.GameService;
import com.roboter5123.backend.service.model.exception.TooManySessionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerImpl implements HttpController {

    private final GameService gameService;

    @Autowired
    public HttpControllerImpl(GameService gameService) {

        this.gameService = gameService;
    }

    @Override
    @PostMapping("/sessions")
    @ResponseBody
    public String createSession(@RequestBody final GameMode gameMode) throws TooManySessionsException {

        return this.gameService.createSession(gameMode);

    }
}
