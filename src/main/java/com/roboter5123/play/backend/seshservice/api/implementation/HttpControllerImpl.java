package com.roboter5123.play.backend.seshservice.api.implementation;
import com.roboter5123.play.backend.seshservice.api.api.HttpController;
import com.roboter5123.play.backend.seshservice.api.model.HttpSeshDTO;
import com.roboter5123.play.backend.seshservice.api.model.exception.BadRequestException;
import com.roboter5123.play.backend.seshservice.api.model.exception.NoSuchSeshHttpException;
import com.roboter5123.play.backend.seshservice.api.model.exception.TooManySeshsHttpException;
import com.roboter5123.play.backend.seshservice.service.api.SeshService;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Log4j2
public class HttpControllerImpl implements HttpController {

    private final SeshService seshService;
    private final ModelMapper modelMapper;

    @Autowired
    public HttpControllerImpl(SeshService seshService, ModelMapper modelMapper) {

        this.seshService = seshService;
        this.modelMapper = modelMapper;
    }

    @Override
    @GetMapping("/seshtypes")
    @ResponseBody
    public List<SeshType> getSeshTypes() {

        log.info("HttpControllerImpl: Entering getSeshtypes()");

        List<SeshType> seshTypes = Arrays.stream(SeshType.values()).filter(seshType -> seshType != SeshType.UNKNOWN).toList();

        log.info("HttpControllerImpl: Exiting getSeshtypes({})", seshTypes);
        return seshTypes;
    }

    @Override
    @PostMapping("/seshs")
    @ResponseBody
    public HttpSeshDTO createSesh(@RequestBody final String seshTypeString) throws TooManySeshsHttpException {

        log.info("HttpControllerImpl: Entering createSesh(seshType={})", seshTypeString);

        try {

            SeshType seshType = SeshType.valueOf(seshTypeString.replace("\"", ""));
            Sesh sesh = this.seshService.createSesh(seshType);
            HttpSeshDTO httpSeshDTO = modelMapper.map(sesh, HttpSeshDTO.class);

            log.info("HttpControllerImpl: Exiting createSesh(httpSeshDTO={})", httpSeshDTO);
            return httpSeshDTO;

        } catch (TooManySeshsException e) {

            log.error("HttpControllerImpl: Exiting createSesh(error={})", e.getMessage());
            throw new TooManySeshsHttpException(e.getMessage());

        } catch (IllegalArgumentException e) {

            log.error("HttpControllerImpl: Exiting createSesh(error={})", e.getMessage());
            throw new BadRequestException("No seshtype with name '" + seshTypeString + "' exists");
        }

    }

    @Override
    @GetMapping("seshs/{seshCode}")
    public HttpSeshDTO getSesh(@PathVariable String seshCode) throws NoSuchSeshHttpException {

        log.info("HttpControllerImpl: Entering getSesh(seshCode={})", seshCode);

        try {
            Sesh sesh = this.seshService.getSesh(seshCode.toUpperCase());
            HttpSeshDTO httpSeshDTO = modelMapper.map(sesh, HttpSeshDTO.class);
            log.info("HttpControllerImpl: Exiting getSesh(httpSeshDTO={})", httpSeshDTO);
            return httpSeshDTO;

        } catch (NoSuchSeshException e) {

            log.error("HttpControllerImpl: Exiting getSesh(error={})", e.getMessage());
            throw new NoSuchSeshHttpException(e.getMessage());
        }
    }
}
