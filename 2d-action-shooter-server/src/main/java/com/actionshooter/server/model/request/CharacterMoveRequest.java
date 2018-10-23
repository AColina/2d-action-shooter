package com.actionshooter.server.model.request;

public class CharacterMoveRequest extends ClientRequest {
    private int x;
    private int y;

    public CharacterMoveRequest() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
