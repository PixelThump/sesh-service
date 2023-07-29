package com.pixelthump.seshservice.rest;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import com.pixelthump.seshservice.rest.model.HttpSeshDTO;
import com.pixelthump.seshservice.rest.model.SeshSeshType;
import com.pixelthump.seshservice.rest.exception.NoSuchSeshHttpException;
import com.pixelthump.seshservice.rest.exception.TooManySeshsHttpException;
import com.pixelthump.seshservice.service.SeshService;
import com.pixelthump.seshservice.service.exception.NoSuchSeshException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
public class SeshResource {

    private final SeshService seshService;
    private final ModelMapper modelMapper;

    @Autowired
    public SeshResource(SeshService seshService, ModelMapper modelMapper) {

        this.seshService = seshService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/seshtypes")
    @ResponseBody
    public List<SeshSeshType> getSeshTypes() {

        log.info("Entering getSeshtypes");

        List<SeshType> seshTypes = seshService.getSeshtypes();
//        @formatter:off
        List<SeshSeshType> stringSeshTypes = modelMapper.map(seshTypes, new TypeToken<List<SeshSeshType>>() {}.getType());
        //        @formatter:on
        log.info("Exiting getSeshtypes with result={}", seshTypes);
        return stringSeshTypes;
    }

    @PostMapping("/seshs")
    @ResponseBody
    public HttpSeshDTO createSesh(@RequestBody final String seshType) throws TooManySeshsHttpException {

        log.info("HttpControllerImpl: Entering createSesh(seshType={})", seshType);
        Sesh sesh = this.seshService.createSesh(seshType);
        HttpSeshDTO httpSeshDTO = modelMapper.map(sesh, HttpSeshDTO.class);
        log.info("HttpControllerImpl: Exiting createSesh(httpSeshDTO={})", httpSeshDTO);
        return httpSeshDTO;
    }

    @GetMapping("/seshs/{seshCode}")
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
