/*
 * Copyright 2017 L0G1C (David B) - logiclodge.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actionshooter.server.unitysocket;

import com.actionshooter.server.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Handle connections from Unity by implementing a Spring
 * {@link WebSocketHandler} abstraction.
 *
 * @author Angel Colina
 * @version 1.0
 */
@Component
public class UnitySocketWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger("UnitySocketWebSocketHandler");

    private final UnitySocketService unitySocketService;
    private final UserController userController;

    @Autowired
    public UnitySocketWebSocketHandler(UnitySocketService unitySocketService, UserController userController) {
        this.unitySocketService = unitySocketService;
        this.userController = userController;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messageString = message.getPayload();
        LOGGER.warn("GOT SOMETHING: {}", messageString);

        this.unitySocketService.parseMessage(messageString, session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.warn("Connection Established.");
        LOGGER.warn("User: " + session.getId());
        userController.onConnect(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.warn("Connection Closed. Status: " + status);
        LOGGER.warn("User: " + session.getId());
        userController.onDisconnect(session);
    }
}
