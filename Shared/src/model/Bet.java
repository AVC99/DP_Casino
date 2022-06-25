package model;

import java.io.Serializable;

public class Bet implements Serializable {
    private int amount;
    private BetType type;

    public Bet(int amount, BetType type) {
        this.amount = amount;
        this.type = type;
    }
    public int getAmount() {
        return amount;
    }

    public BetType getType() {
        return type;
    }
}
