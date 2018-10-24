package com.actionshooter.server.core;

import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author Angel Colina
 * @version 1.0
 */
public class SessionRegistry {

    private static SessionRegistry sessionRegistry;
    private Map<String, WebSocketSession> sessions;
    private Map<String, String> names;

    private SessionRegistry() {
        sessions = new CaseInsensitiveKeyMap<>();
        names = new HashMap<>();
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

    public boolean containsName(String name) {
        return names.containsKey(name);
    }

    public WebSocketSession get(String key) {
        return sessions.get(key);
    }

    public WebSocketSession put(WebSocketSession value) {
        return sessions.put(value.getId(), value);
    }

    public WebSocketSession remove(WebSocketSession value) {
        String removeName;
        return sessions.remove(value.getId());
    }

    public void setName(String name, WebSocketSession value) {
        setName(name, value.getId());
    }

    public void setName(String name, String idSession) {
        names.put(name, idSession);
    }

    public WebSocketSession getByName(String name) {
        return sessions.get(names.get(name));
    }

    public String getNameBySession(WebSocketSession session) {
        return getNameBySession(session.getId());
    }

    public Set<String> getListUsers(WebSocketSession session) {
        String sessionName = this.getNameBySession(session);

        return names.keySet().stream()
                .filter(s -> !s.equals(sessionName))
                .collect(Collectors.toSet());
    }

    public String getNameBySession(String idSession) {
        String name = null;
        for (Map.Entry<String, String> value : names.entrySet()) {
            if (value.getValue().equals(idSession)) {
                name = value.getKey();
                break;
            }
        }
        return name;
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
