package com.roboter5123.play.backend.api.implementation;
import com.roboter5123.play.backend.api.api.HttpController;
import com.roboter5123.play.backend.api.model.HttpSeshDTO;
import com.roboter5123.play.backend.api.model.exception.BadRequestException;
import com.roboter5123.play.backend.api.model.exception.NoSuchSeshHttpException;
import com.roboter5123.play.backend.api.model.exception.TooManySeshsHttpException;
import com.roboter5123.play.backend.sesh.api.Sesh;
import com.roboter5123.play.backend.sesh.api.SeshType;
import com.roboter5123.play.backend.sesh.implementation.chat.ChatSesh;
import com.roboter5123.play.backend.messaging.api.MessageBroadcaster;
import com.roboter5123.play.backend.messaging.api.StompMessageFactory;
import com.roboter5123.play.backend.service.api.SeshService;
import com.roboter5123.play.backend.service.api.SeshManager;
import com.roboter5123.play.backend.service.exception.NoSuchSeshException;
import com.roboter5123.play.backend.service.exception.TooManySeshsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class HttpControllerImplTest {

    @MockBean
    SeshService seshServiceMock;
    @MockBean
    MessageBroadcaster broadcasterMock;
    @MockBean
    StompMessageFactory factoryMock;

    @MockBean
    SeshManager seshManager;
    @Autowired
    HttpController httpController;
    String sessionCode;
    Sesh sesh;

    @BeforeEach
    void setUp() {

        sessionCode = "abcd";
        this.sesh = new ChatSesh(broadcasterMock);
        this.sesh.setSeshType(SeshType.CHAT);
        this.sesh.setSeshCode(sessionCode);
    }

    @Test
    void get_Game_Modes_should_return_all_game_modes_but_unknown(){

        List<SeshType> expected = Arrays.stream(SeshType.values()).filter(gameMode -> gameMode!= SeshType.UNKNOWN).toList();
        List<SeshType> result = httpController.getSeshTypes();

        assertEquals(expected, result);
    }

    @Test
    void create_Session_should_return_http_game() throws TooManySeshsException {

        when(seshServiceMock.createSesh(SeshType.CHAT)).thenReturn(sesh);

        HttpSeshDTO expected = new HttpSeshDTO(sesh.getSeshType(), sesh.getSeshCode());
        HttpSeshDTO result = httpController.createSesh(SeshType.CHAT.name());

        assertEquals(expected, result);
    }

    @Test
    void create_Session_with_too_many_sessions_should_throw_too_many_sessions_exception() throws TooManySeshsException {

        when(seshServiceMock.createSesh(any())).thenThrow(new TooManySeshsException("Unable to create session because there were too many sessions"));
        String gameMode = SeshType.UNKNOWN.name();
        assertThrows(TooManySeshsHttpException.class, ()-> httpController.createSesh(gameMode));
    }

    @Test
    void create_Session_with_too_many_sessions_should_throw_bad_request_exception() throws TooManySeshsException {
        String gameMode = "LOL";
        when(seshServiceMock.createSesh(any())).thenThrow(new BadRequestException("No Gamemode with name '" + gameMode + "' exists"));
        assertThrows(BadRequestException.class, ()-> httpController.createSesh(gameMode));
    }

    @Test
    void get_game_should_return_game() {

        when(seshServiceMock.getSesh(any())).thenReturn(sesh);

        HttpSeshDTO expected = new HttpSeshDTO(SeshType.CHAT, sessionCode);
        HttpSeshDTO result = httpController.getSesh(sessionCode);

        assertEquals(expected, result);
    }

    @Test
    void get_game_should_throw_so_such_session_exception() {

        when(seshServiceMock.getSesh(any())).thenThrow(new NoSuchSeshException("No session with code " + sessionCode + " exists"));
        assertThrows(NoSuchSeshHttpException.class, ()-> httpController.getSesh(sessionCode));
    }
}