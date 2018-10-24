package com.actionshooter.server.model.request;

import com.actionshooter.server.core.model.response.ClientResponse;
import com.actionshooter.server.core.model.response.ResponseType;

import java.util.Set;

public class PlayerListResponse extends ClientResponse {
    private Set<String> users;

    public PlayerListResponse() {
        setType(ResponseType.PLAYERLIST);
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }
}
