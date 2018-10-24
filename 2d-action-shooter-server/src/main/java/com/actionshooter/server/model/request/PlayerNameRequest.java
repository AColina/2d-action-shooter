package com.actionshooter.server.model.request;

import com.actionshooter.server.core.model.request.ClientRequest;

public class PlayerNameRequest extends ClientRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
