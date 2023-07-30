package com.pixelthump.seshservice.rest;
import com.pixelthump.seshservice.Application;
import com.pixelthump.seshservice.repository.model.Sesh;
import com.pixelthump.seshservice.repository.model.SeshType;
import com.pixelthump.seshservice.rest.model.HttpSeshDTO;
import com.pixelthump.seshservice.rest.model.SeshSeshType;
import com.pixelthump.seshservice.service.SeshService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
class SeshResourceTest {

    @Autowired
    private SeshResource seshResource;

    @MockBean
    private SeshService seshService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getSeshTypes_shouldMapCorrectly() {

        List<SeshType> serviceResult = new ArrayList<>();
        serviceResult.add(new SeshType("quizxel"));
        when(seshService.getSeshtypes()).thenReturn(serviceResult);
        List<SeshSeshType> result = seshResource.getSeshTypes();
        List<SeshSeshType> expected = new ArrayList<>();
        expected.add(new SeshSeshType("quizxel"));
        assertEquals(expected, result);
    }

    @Test
    void createSesh_shouldMapCorrectly() {

        Sesh quizxelSesh = new Sesh(new SeshType("quizxel"), "abcd");
        when(seshService.createSesh("quizxel")).thenReturn(quizxelSesh);
        HttpSeshDTO result = seshResource.createSesh("quizxel");
        HttpSeshDTO expected = new HttpSeshDTO("quizxel", "abcd");
        assertEquals(expected, result);
    }

    @Test
    void getSesh_shouldMapCorrectly() {

        Sesh quizxelSesh = new Sesh(new SeshType("quizxel"), "abcd");
        when(seshService.getSesh("ABCD")).thenReturn(quizxelSesh);
        HttpSeshDTO result = seshResource.getSesh("abcd");
        HttpSeshDTO expected = new HttpSeshDTO("quizxel", "abcd");
        assertEquals(expected, result);
    }
}