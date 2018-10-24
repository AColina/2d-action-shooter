package com.actionshooter.server.controller;

import com.actionshooter.server.core.ClientEvent;
import com.actionshooter.server.core.SessionRegistry;
import com.actionshooter.server.model.request.PlayerListRequest;
import com.actionshooter.server.model.request.PlayerListResponse;
import com.actionshooter.server.model.request.PlayerMoveRequest;
import com.actionshooter.server.model.request.PlayerNameRequest;
import com.actionshooter.server.unitysocket.UnitySocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger("UserController");

    @Autowired
    private UnitySocketService unitySocketService;

    public void onConnect(WebSocketSession session) {
        SessionRegistry registry = SessionRegistry.getSessionRegistry();
        registry.put(session);
    }

    public void onDisconnect(WebSocketSession session) {
        SessionRegistry registry = SessionRegistry.getSessionRegistry();
        registry.remove(session);
    }

    @EventListener
    public void onCharacterMoveRequest(ClientEvent<PlayerMoveRequest> event) {
//        System.out.println("move : x = " + event.getClientRequest().getX() + " y : " + event.getClientRequest().getY());
    }

    @EventListener
    public void onPlayerChangeName(ClientEvent<PlayerNameRequest> event) {
        SessionRegistry registry = SessionRegistry.getSessionRegistry();
        registry.setName(event.getClientRequest().getName(), event.getSession());
    }

    @EventListener
    public void onPlayerList(ClientEvent<PlayerListRequest> event) throws IOException {
        SessionRegistry registry = SessionRegistry.getSessionRegistry();
//        registry.setName(event.getClientRequest().getName(), event.getSession());
        PlayerListResponse response = new PlayerListResponse();
        response.setUsers(registry.getListUsers(event.getSession()));
        unitySocketService.sendMessage(response, event.getSession());
    }
}
