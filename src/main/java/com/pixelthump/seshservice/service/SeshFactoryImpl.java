package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import com.pixelthump.seshservice.rest.model.HttpSeshDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class SeshFactoryImpl implements SeshFactory {

    @Value("${pixelthump.backend-basepath}")
    private String backendBasePath;
    private final RestTemplate restTemplate;

    private final SeshTypeService seshTypeService;

    public SeshFactoryImpl(SeshTypeService seshTypeService) {

        this.seshTypeService = seshTypeService;
        restTemplate = new RestTemplate();
    }

    @Override
    public Sesh createSesh(String seshCode, SeshType seshType) {

        String apiUrl = backendBasePath + "/" + seshType.getName() + "/seshs";
        ResponseEntity<HttpSeshDTO> response = restTemplate.postForEntity(apiUrl, seshCode, HttpSeshDTO.class);
        return convertHttpSeshDTOToSesh(Objects.requireNonNull(response.getBody()));
    }

    private Sesh convertHttpSeshDTOToSesh(HttpSeshDTO response) {

        SeshType seshType = seshTypeService.getSeshTypeByName(response.getSeshType());
        String seshCode = response.getSeshCode();
        return new Sesh(seshCode,seshType);
    }
}
