package com.pixelthump.seshservice.service;
import com.pixelthump.seshservice.Application;
import com.pixelthump.seshservice.repository.SeshTypeRepository;
import com.pixelthump.seshservice.repository.model.SeshType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Application.class)
class SeshTypeServiceImplTest {

    @Autowired
    SeshTypeService seshTypeService;
    @MockBean
    SeshTypeRepository seshTypeRepository;

    @Test
    void getSeshtypes_shouldReturnCorrectList() {

        List<SeshType> seshTypeList = new ArrayList<>();
        seshTypeList.add(new SeshType("quizxel"));
        when(seshTypeRepository.findAll()).thenReturn(seshTypeList);

        List<SeshType> result = seshTypeService.getSeshtypes();
        assertEquals(seshTypeList, result);
    }

    @Test
    void getSeshTypeByName_shouldReturnCorrectType() {

        SeshType seshType = new SeshType("quizxel");
        when(seshTypeRepository.findByName("quizxel")).thenReturn(Optional.of(seshType));

        SeshType result = seshTypeService.getSeshTypeByName("quizxel");
        assertEquals(seshType, result);
    }

    @Test
    void getSeshTypeByName_shouldThrowException() {

        when(seshTypeRepository.findByName("quizxel")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> seshTypeService.getSeshTypeByName("quizxel"));
        assertEquals(HttpStatusCode.valueOf(404), exception.getStatusCode());
    }
}