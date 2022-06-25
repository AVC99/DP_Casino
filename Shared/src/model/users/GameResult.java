package model.users;

import java.io.Serializable;

public class GameResult implements Serializable {
    private int amount;
    private String gameType;
    private Boolean isVictory;
    private String username;
    private Guest user;





    public GameResult(int amount, String game_type, Boolean isVictory,  String userName, Guest user) {
        this.amount = amount;
        this.gameType = game_type;
        this.isVictory = isVictory;
        this.username = userName;
        this.user = user;

    }



    public Guest getUser() {
        return user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Boolean getIsVictory() {
        return isVictory;
    }

    public void setIsVictory(Boolean isVictory) {
        this.isVictory = isVictory;
    }

    public Boolean getVictory() {
        return isVictory;
    }

    public String getUsername() {
        return username;
    }

}
