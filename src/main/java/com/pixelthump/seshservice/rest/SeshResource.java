package com.pixelthump.seshservice.rest;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import com.pixelthump.seshservice.rest.model.HttpSeshDTO;
import com.pixelthump.seshservice.rest.model.SeshSeshType;
import com.pixelthump.seshservice.service.SeshService;
import com.pixelthump.seshservice.service.SeshTypeService;
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
    private final SeshTypeService seshTypeService;
    private final ModelMapper modelMapper;

    @Autowired
    public SeshResource(SeshService seshService, SeshTypeService seshTypeService, ModelMapper modelMapper) {

        this.seshService = seshService;
        this.seshTypeService = seshTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/seshtypes")
    @ResponseBody
    public List<SeshSeshType> getSeshTypes() {

        log.info("Entering getSeshtypes");

        List<SeshType> seshTypes = seshTypeService.getSeshtypes();
//        @formatter:off
        List<SeshSeshType> stringSeshTypes = modelMapper.map(seshTypes, new TypeToken<List<SeshSeshType>>() {}.getType());
        //        @formatter:on
        log.info("Exiting getSeshtypes with result={}", seshTypes);
        return stringSeshTypes;
    }

    @PostMapping("/seshs")
    @ResponseBody
    public HttpSeshDTO createSesh(@RequestBody final String seshType){

        log.info("HttpControllerImpl: Entering createSesh(seshType={})", seshType);
        Sesh sesh = this.seshService.createSesh(seshType);
        HttpSeshDTO httpSeshDTO = modelMapper.map(sesh, HttpSeshDTO.class);
        log.info("HttpControllerImpl: Exiting createSesh(httpSeshDTO={})", httpSeshDTO);
        return httpSeshDTO;
    }

    @GetMapping("/seshs/{seshCode}")
    public HttpSeshDTO getSesh(@PathVariable String seshCode){

        log.info("HttpControllerImpl: Entering getSesh(seshCode={})", seshCode);
        Sesh sesh = this.seshService.getSesh(seshCode.toUpperCase());
        HttpSeshDTO httpSeshDTO = modelMapper.map(sesh, HttpSeshDTO.class);
        log.info("HttpControllerImpl: Exiting getSesh(httpSeshDTO={})", httpSeshDTO);
        return httpSeshDTO;
    }
}
