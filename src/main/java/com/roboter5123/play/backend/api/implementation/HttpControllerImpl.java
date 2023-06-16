package com.roboter5123.play.backend.api.implementation;
import com.roboter5123.play.backend.api.api.HttpController;
import com.roboter5123.play.backend.api.model.HttpGameDTO;
import com.roboter5123.play.backend.api.model.exception.BadRequestException;
import com.roboter5123.play.backend.api.model.exception.NoSuchSessionHttpException;
import com.roboter5123.play.backend.api.model.exception.TooManySessionsHttpException;
import com.roboter5123.play.backend.game.api.Game;
import com.roboter5123.play.backend.game.api.GameMode;
import com.roboter5123.play.backend.service.api.GameService;
import com.roboter5123.play.backend.service.exception.NoSuchSessionException;
import com.roboter5123.play.backend.service.exception.TooManySessionsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class HttpControllerImpl implements HttpController {

    private final GameService gameService;
    private final ModelMapper modelMapper;

    @Autowired
    public HttpControllerImpl(GameService gameService, ModelMapper modelMapper) {

        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping("/gamemodes")
    @ResponseBody
    public List<GameMode> getGameModes() {

        GameMode[] gameModes = GameMode.values();
        return Arrays.stream(gameModes).filter(gameMode -> gameMode != GameMode.UNKNOWN).toList();
    }

    @Override
    @PostMapping("/sessions")
    @ResponseBody
    public HttpGameDTO createSession(@RequestBody final String gameModeString) throws TooManySessionsHttpException {

        try {

            GameMode gameMode = GameMode.valueOf(gameModeString);
            Game game = this.gameService.createSession(gameMode);
            return modelMapper.map(game, HttpGameDTO.class);

        } catch (TooManySessionsException e) {

            throw new TooManySessionsHttpException(e.getMessage());

        }catch (IllegalArgumentException e){

            throw new BadRequestException("No Gamemode with name '" + gameModeString + "' exists");
        }

    }

    @Override
    @GetMapping("sessions/{sessionCode}")
    public HttpGameDTO getGame(@PathVariable String sessionCode) throws NoSuchSessionHttpException {

        try {
            Game game = this.gameService.getGame(sessionCode.toUpperCase());
            return modelMapper.map(game, HttpGameDTO.class);

        } catch (NoSuchSessionException e) {

            throw new NoSuchSessionHttpException(e.getMessage());
        }
    }
}
