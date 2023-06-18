package com.roboter5123.play.backend.seshservice.api.implementation;
import com.roboter5123.play.backend.seshservice.api.api.HttpController;
import com.roboter5123.play.backend.seshservice.api.model.HttpSeshDTO;
import com.roboter5123.play.backend.seshservice.api.model.exception.BadRequestException;
import com.roboter5123.play.backend.seshservice.api.model.exception.NoSuchSeshHttpException;
import com.roboter5123.play.backend.seshservice.api.model.exception.TooManySeshsHttpException;
import com.roboter5123.play.backend.seshservice.sesh.api.Sesh;
import com.roboter5123.play.backend.seshservice.sesh.api.SeshType;
import com.roboter5123.play.backend.seshservice.service.api.SeshService;
import com.roboter5123.play.backend.seshservice.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.seshservice.service.exception.TooManySeshsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
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

        SeshType[] seshTypes = SeshType.values();
        return Arrays.stream(seshTypes).filter(seshType -> seshType != SeshType.UNKNOWN).toList();
    }

    @Override
    @PostMapping("/seshs")
    @ResponseBody
    public HttpSeshDTO createSesh(@RequestBody final String seshTypeString) throws TooManySeshsHttpException {

        try {

            SeshType seshType = SeshType.valueOf(seshTypeString.replace("\"",""));
            Sesh sesh = this.seshService.createSesh(seshType);
            return modelMapper.map(sesh, HttpSeshDTO.class);

        } catch (TooManySeshsException e) {

            throw new TooManySeshsHttpException(e.getMessage());

        }catch (IllegalArgumentException e){

            throw new BadRequestException("No seshtype with name '" + seshTypeString + "' exists");
        }

    }

    @Override
    @GetMapping("seshs/{seshCode}")
    public HttpSeshDTO getSesh(@PathVariable String seshCode) throws NoSuchSeshHttpException {

        try {
            Sesh sesh = this.seshService.getSesh(seshCode.toUpperCase());
            return modelMapper.map(sesh, HttpSeshDTO.class);

        } catch (NoSuchSeshException e) {

            throw new NoSuchSeshHttpException(e.getMessage());
        }
    }
}
