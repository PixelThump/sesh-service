package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.Application;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import com.pixelthump.seshservice.rest.model.HttpSeshDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
class SeshFactoryImplTest {

    @Autowired
    SeshFactory seshFactory;
    @MockBean
    RestTemplate restTemplate;
    @MockBean
    SeshTypeService seshTypeService;

    @Value("${pixelthump.backend-basepath}")
    private String backendBasePath;

    @Test
    void createSesh_sould_call_resttemplateAndConvertCorrectly() {

        SeshType quizxelSeshType = new SeshType("quizxel");
        when(seshTypeService.getSeshTypeByName("quizxel")).thenReturn(quizxelSeshType);

        String apiUrlExpected = backendBasePath + "/quizxel/seshs";
        ResponseEntity<HttpSeshDTO> seshResponseEntity = new ResponseEntity<>(new HttpSeshDTO("quizxel", "abcd"), HttpStatusCode.valueOf(200));
        when(restTemplate.postForEntity(apiUrlExpected, "abcd", HttpSeshDTO.class)).thenReturn(seshResponseEntity);

        Sesh result = seshFactory.createSesh("abcd", quizxelSeshType);

        verify(seshTypeService).getSeshTypeByName("quizxel");
        verify(restTemplate).postForEntity(apiUrlExpected, "abcd", HttpSeshDTO.class);

        Sesh expected = new Sesh(quizxelSeshType, "abcd");
        assertEquals(expected, result);
    }
}