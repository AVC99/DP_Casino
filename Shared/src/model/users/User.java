package model.users;

public class User extends Guest {
    private String password;


    public User(String username, int balance, String password) {
        this.username = username;
        this.balance = balance;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
