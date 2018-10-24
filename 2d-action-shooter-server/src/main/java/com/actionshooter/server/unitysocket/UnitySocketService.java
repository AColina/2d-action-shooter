package com.actionshooter.server.unitysocket;

import com.actionshooter.server.core.ClientEvent;
import com.actionshooter.server.core.model.request.ClientRequest;
import com.actionshooter.server.core.model.response.ClientResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 *
 * @author Angel Colina
 * @version 1.0
 */
@Service
public class UnitySocketService {

	private static final Logger LOGGER = LoggerFactory.getLogger("UnitySocketService");

	private final ObjectMapper objectMapper;
	private final ApplicationEventPublisher publisher;

	@Autowired
	public UnitySocketService(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
		objectMapper = new ObjectMapper();
	}

	public void parseMessage(String messageString, WebSocketSession session) throws IOException {
		ClientRequest cr = objectMapper.readValue(messageString, ClientRequest.class);

		publisher.publishEvent(new ClientEvent<>(cr, session));
	}

	public void sendMessage(ClientResponse clientResponse, WebSocketSession session) throws IOException {
		String json = objectMapper.writeValueAsString(clientResponse);
		LOGGER.warn("sending message: {}", json);
		session.sendMessage(new TextMessage(json));
	}
}
