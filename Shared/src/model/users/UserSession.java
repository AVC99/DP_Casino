package model.users;

import java.io.Serializable;

public class UserSession implements Serializable {
    private String username;
    private String password;

    public UserSession(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
