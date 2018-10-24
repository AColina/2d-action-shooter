package com.actionshooter.server.core;

import com.actionshooter.server.core.model.request.ClientRequest;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;
import org.springframework.web.socket.WebSocketSession;

public class ClientEvent<T extends ClientRequest> implements ResolvableTypeProvider {

	private final T clientRequest;
	private final WebSocketSession session;

	public ClientEvent(T clientRequest, WebSocketSession session) {
		this.clientRequest = clientRequest;
		this.session = session;
	}

	public T getClientRequest() {
		return clientRequest;
	}

	public WebSocketSession getSession() {
		return session;
	}

	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(clientRequest));
	}

}