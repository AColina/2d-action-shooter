package com.actionshooter.server.controller;

import com.actionshooter.server.model.ClientEvent;
import com.actionshooter.server.model.request.CharacterMoveRequest;
import com.actionshooter.server.unitysocket.UnitySocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger("UserController");

    @Autowired
    private UnitySocketService unitySocketService;


    @EventListener
    public void onCharacterMoveRequest(ClientEvent<CharacterMoveRequest> event) {
        LOGGER.warn("Got Friends List Request");

        WebSocketSession session = event.getSession();
//        try {
//            unitySocketService.sendMessage(getFriendsListResponse(session), session);
            LOGGER.warn("Sending extra dummy response.  Client should be able to deal with it.");
//            unitySocketService.sendMessage(new KemonomimiResponse(), session);
//        } catch (IOException ex) {
//            LOGGER.error("Something Bad Happened", ex);
//        }
    }

}
