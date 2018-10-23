package com.actionshooter.server.core;

import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author Angel Colina
 * @version 1.0
 */
public class SessionRegistry {

    private static SessionRegistry sessionRegistry;
    private Map<String, WebSocketSession> sessions;

    private SessionRegistry() {
        sessions = new CaseInsensitiveKeyMap<>();
    }

    public int size() {
        return sessions.size();
    }

    public boolean isEmpty() {
        return sessions.isEmpty();
    }

    public boolean containsKey(String key) {
        return sessions.containsKey(key);
    }

    public boolean containsValue(WebSocketSession value) {
        return sessions.containsValue(value);
    }

    public WebSocketSession get(String key) {
        return sessions.get(key);
    }

    public WebSocketSession put(WebSocketSession value) {
        return sessions.put(value.getId(), value);
    }

    public WebSocketSession remove(WebSocketSession value) {
        return sessions.remove(value.getId());
    }

    public static SessionRegistry getSessionRegistry() {
        if (Objects.isNull(sessionRegistry)) {
            sessionRegistry = new SessionRegistry();
        }
        return sessionRegistry;
    }

    public void forEach(BiConsumer<? super String, ? super WebSocketSession> action) {
        sessions.forEach(action);
    }
}
