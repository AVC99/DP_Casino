package model.users;

import java.io.Serializable;

public class Guest implements Serializable {
    private static int GUEST_COUNTER = 1;
    protected int balance;
    protected String username;
    public static int MAX_FUNDS=100000;

    public Guest() {
        this.balance = 15000;
        this.username = "Guest-" + GUEST_COUNTER;
        GUEST_COUNTER++;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }
}
