package model.network;

import java.io.Serializable;

public class SocketData implements Serializable {
    private SocketActions action;
    private String data;

    public SocketData(SocketActions action, String data) {
        this.action = action;
        this.data = data;
    }

    public SocketActions getAction() {
        return action;
    }

    public String getData() {
        return data;
    }
}