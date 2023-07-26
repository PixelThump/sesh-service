package com.pixelthump.seshservice.implementation;
import com.pixelthump.seshservice.messaging.api.MessageBroadcaster;
import com.pixelthump.seshservice.messaging.model.Command;
import com.pixelthump.seshservice.messaging.model.action.Action;
import com.pixelthump.seshservice.messaging.model.message.CommandStompMessage;
import com.pixelthump.seshservice.service.api.SeshManager;
import com.pixelthump.seshservice.service.api.SeshService;
import com.pixelthump.seshservice.service.exception.NoSuchSeshException;
import com.pixelthump.seshservice.service.exception.TooManySeshsException;
import com.pixelthump.seshservice.sesh.api.Sesh;
import com.pixelthump.seshservice.sesh.model.SeshType;
import com.pixelthump.seshservice.sesh.model.state.AbstractSeshState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SeshServiceImplTest {

	@MockBean
    MessageBroadcaster broadcaster;
	@MockBean
    SeshManager sessionManager;
	@Autowired
    SeshService seshService;
	String sessionCode;
	Sesh sesh;
	private String playerName;

	@BeforeEach
	void setup() {

		this.sessionCode = "abcd";
		this.playerName = "roboter5123";
		sesh = Mockito.mock(Sesh.class);
	}

	@Test
	void create_session_should_return_game() throws TooManySeshsException {

		when(sessionManager.createSesh(any())).thenReturn(sesh);

		Sesh expected = sesh;
		Sesh result = seshService.createSesh(SeshType.UNKNOWN);
		assertEquals(expected, result);
	}

	@Test
	void create_session_should_throw_TooManySessionsException() {

		when(sessionManager.createSesh(any())).thenThrow(new TooManySeshsException("Unable to create session because there were too many sessions"));
		assertThrows(TooManySeshsException.class, () -> seshService.createSesh(SeshType.UNKNOWN));
	}

	@Test
	void get_game_should_return_optional_of_game() {

		when(sessionManager.getSesh(any())).thenReturn(sesh);

		Sesh expected = sesh;
		Sesh result = seshService.getSesh(sessionCode);
		assertEquals(expected, result);
	}

	@Test
	void get_game_should_return_empty_optional() {

		when(sessionManager.getSesh(any())).thenThrow(new NoSuchSeshException("Could not join session with code " + sessionCode + ".Session not found."));
		assertThrows(NoSuchSeshException.class, () -> seshService.getSesh(sessionCode));
	}

	@Test
	void joinSeshAsHost_should_return_state() {

		AbstractSeshState expected = new AbstractSeshState();

		when(sessionManager.getSesh(sessionCode)).thenReturn(sesh);

		when(sesh.joinSeshAsHost(any())).thenReturn(expected);

		AbstractSeshState result = seshService.joinSeshAsHost(sessionCode, playerName);
		assertEquals(expected, result);
	}

	@Test
	void joinSeshAsHost_should_throw_no_such_session_exception() {

		when(sessionManager.getSesh(any())).thenThrow(new NoSuchSeshException("Could not join session with code " + sessionCode + ".Session not found."));

		assertThrows(NoSuchSeshException.class, () -> seshService.joinSeshAsHost(sessionCode, playerName));
	}

	@Test
	void joinSeshAsController_should_return_state() {

		AbstractSeshState expected = new AbstractSeshState();

		when(sessionManager.getSesh(sessionCode)).thenReturn(sesh);

		when(sesh.joinSeshAsController(any(), any())).thenReturn(expected);

		AbstractSeshState result = seshService.joinSeshAsController(sessionCode, playerName, playerName);
		assertEquals(expected, result);
	}

	@Test
	void joinSeshAsController_should_throw_no_such_session_exception() {

		when(sessionManager.getSesh(any())).thenThrow(new NoSuchSeshException("Could not join session with code " + sessionCode + ".Session not found."));

		assertThrows(NoSuchSeshException.class, () -> seshService.joinSeshAsController(sessionCode, playerName, playerName));
	}

	@Test
	void sendCommandToGame_should_return_Acknowledment_Message() {

		when(sessionManager.getSesh(sessionCode)).thenReturn(sesh);

		Command incomingCommand = new Command(playerName, new Action<>(playerName, "Chat message"));
		CommandStompMessage incomingMessage = new CommandStompMessage(incomingCommand);

		seshService.sendCommandToSesh(incomingMessage, sessionCode);

		verify(sesh).addCommand(incomingCommand);
	}

	@Test
	void sendCommandToGame_should_log_error_and_throw_no_such_session_exception() {

		NoSuchSeshException exception = new NoSuchSeshException("Could not join session with code " + sessionCode + ".Session not found.");
		when(sessionManager.getSesh(any())).thenThrow(exception);

		Command incomingCommand = new Command(playerName, new Action<>(playerName, "Chat message"));
		CommandStompMessage incomingMessage = new CommandStompMessage(incomingCommand);
		assertThrows(NoSuchSeshException.class, () -> seshService.sendCommandToSesh(incomingMessage, sessionCode));
	}
}
