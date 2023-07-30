package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.Application;
import com.pixelthump.seshservice.repository.SeshRepository;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
class SeshServiceImplTest {

    @Autowired
    SeshService seshService;
    @MockBean
    SeshRepository seshRepository;
    @MockBean
    SeshTypeService seshTypeService;
    @MockBean
    private SeshFactory seshFactory;

    @Test
    void createSesh_wrongSeshType_shouldThrowBadRequestException() {

        when(seshTypeService.getSeshTypeByName("quizxel")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> seshService.createSesh("quizxel"));
        assertEquals(HttpStatusCode.valueOf(400), exception.getStatusCode());
    }

    @Test
    void createSesh_shouldThrowConflictException() {

        List<Sesh> seshList = new ArrayList<>();

        for (int i = 0; i < 456976; i++) {

            seshList.add(new Sesh(new SeshType("quizxel"), "abcd"));
        }

        when(seshRepository.findAll()).thenReturn(seshList);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> seshService.createSesh("quizxel"));
        assertEquals(HttpStatusCode.valueOf(409), exception.getStatusCode());
    }

    @Test
    void createSesh_shouldReturnSesh() {

        Sesh expected = new Sesh(new SeshType("quizxel"), "abcd");
        when(seshFactory.createSesh(any(), eq(new SeshType("quizxel")))).thenReturn(expected);
        when(seshRepository.save(any())).thenReturn(expected);
        Sesh result = seshService.createSesh("quizxel");
        assertEquals(expected, result);
    }

    @Test
    void getSesh_doesntexist_shouldThrowBadRequest() {

        when(seshRepository.findBySeshCode("abcd")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> seshService.getSesh("abcd"));
        assertEquals(HttpStatusCode.valueOf(404), exception.getStatusCode());
    }

    @Test
    void getSesh_shouldReturnSesh() {

        Sesh expected = new Sesh(new SeshType("quizxel"), "abcd");
        when(seshRepository.findBySeshCode("abcd")).thenReturn(Optional.of(expected));
        Sesh result = seshService.getSesh("abcd");
        assertEquals(expected, result);
    }
}