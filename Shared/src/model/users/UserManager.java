package model.users;

import constants.ResourcesPath;
import model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class UserManager {
    private Guest user;
    private ArrayList<Bet> betList;


    public UserManager(Guest user) {
        this.user = user;
        this.betList = new ArrayList<>();
    }

    public void addBet(Bet bet) {
        if (bet.getAmount() > user.getBalance()) {
            JOptionPane.showMessageDialog(null, "You don't have enough money to place this bet");
        } else {
            betList.add(bet);
            System.out.println("bet added of :" + bet.getAmount() + "to the betlist by user: " + user.getUsername());
            subtractFunds(bet.getAmount());
        }
    }


    public Guest getUser() {
        return user;
    }

    public void addFunds(int number) {
        // Check that a user cannot have more than Balance.MAX_FUNDS as total balance
        if ((this.user.getBalance() + number) > Guest.MAX_FUNDS) {
            this.user.setBalance(Guest.MAX_FUNDS);
        } else {
            this.user.setBalance(this.user.getBalance() + number);
        }
    }
    public int payHorseBet(Horse winningHorse){
        int balance=0;
        for(Bet b: betList){
            if(b.getType()== BetType.HORSE_RACE_BLUE && Objects.equals(winningHorse.getHorseColor(), ResourcesPath.HORSE_COLORS[1])){
                user.setBalance(user.getBalance()+(3*b.getAmount()));
                balance+=3*b.getAmount();
            }else if(b.getType()== BetType.HORSE_RACE_RED && Objects.equals(winningHorse.getHorseColor(), ResourcesPath.HORSE_COLORS[0])){
                user.setBalance(user.getBalance()+(3*b.getAmount()));
                balance+=3*b.getAmount();
            }else if (b.getType()== BetType.HORSE_RACE_PURPLE && Objects.equals(winningHorse.getHorseColor(), ResourcesPath.HORSE_COLORS[2])){
                user.setBalance(user.getBalance()+(3*b.getAmount()));
                balance+=3*b.getAmount();
            }
        }
        return balance;
    }

    public void logout() {
        this.user = null;
    }

    public void subtractFunds(int amount) {
        System.out.println("user Balcaen before subtraction: " + user.getBalance());
        System.out.println("amount to subtract: " + amount);
        System.out.println("user balance after subtraction: " + (user.getBalance() - amount));
        this.user.setBalance(this.user.getBalance() - amount);
    }

    public ArrayList<Bet> getBetList() {
        return betList;
    }


    public int payBlackJackBet(BlackJackResults blackJackResults) {
        int balance=0;
        for (Bet b : betList) {
            switch (blackJackResults){
                case PLAYER_WIN -> {
                    user.setBalance(user.getBalance() + 2*b.getAmount());
                    balance+=2*b.getAmount();

                }
                case PLAYER_LOSE -> {
                    //user.setBalance(user.getBalance() - b.getAmount());
                    balance-=b.getAmount();
                }
                case DRAW -> {user.setBalance(user.getBalance() + b.getAmount());}
            }
        }
        return balance;
    }

    public int payRouletteBet(int winningNumber, ArrayList<RouletteCell>rouletteCells) {
        String winningColor = "";
        int  amount=0;
        for (RouletteCell r : rouletteCells) {
            if (winningNumber == r.getValue()) winningColor = r.getColor();
        }

        for (Bet b : betList) {
            if (Objects.equals(winningColor, RouletteCell.RED) && b.getType().equals(BetType.ROULETTE_RED)) {
                System.out.println("paying for red " + 2 * b.getAmount());
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount += 2 * b.getAmount();
            } else if (Objects.equals(winningColor, RouletteCell.BLACK) && b.getType().equals(BetType.ROULETTE_BLACK)) {
                System.out.println("paying for black " + 2 * b.getAmount());
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount+= 2 * b.getAmount();
            } else if (winningNumber >= 1 && winningNumber <= 18 && b.getType() == BetType.ROULETTE_1_18) {
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount+= 2 * b.getAmount();
            } else if (winningNumber >= 19 && winningNumber <= 36 && b.getType() == BetType.ROULETTE_19_36) {
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount+= 2 * b.getAmount();
            } else if (winningNumber % 2 == 0 && b.getType().equals(BetType.ROULETTE_EVEN)) { //even
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount+= 2 * b.getAmount();
            } else if (winningNumber % 2 != 0 && b.getType().equals(BetType.ROULETTE_ODD)) { //odd
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount+= 2 * b.getAmount();
            }
            if (winningNumber <= 12 && b.getType().equals(BetType.ROULETTE_FIRST_12)) { //first 12
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount+= 2 * b.getAmount();
            } else if (winningNumber <= 24 && b.getType().equals(BetType.ROULETTE_SECOND_12)) { //second 12
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount+= 2 * b.getAmount();
            } else if (winningNumber <= 36 && b.getType().equals(BetType.ROULETTE_THIRD_12)) { //third 12
                user.setBalance(user.getBalance() + (2 * b.getAmount()));
                amount+= 2 * b.getAmount();
            }

            if(b.getType().equals(BetType.ROULETTE_FIRST_ROW)){
                for (int i = 1; i <= 34; i += 3) {
                    if (i == winningNumber && b.getType().equals(BetType.ROULETTE_FIRST_ROW))
                        user.setBalance(user.getBalance() + (3 * b.getAmount()));
                    amount+= 3 * b.getAmount();
                }
            }
            else if (b.getType().equals(BetType.ROULETTE_SECOND_ROW)){
                for (int i = 2; i <= 35; i += 3) {
                    if (i == winningNumber && b.getType().equals(BetType.ROULETTE_SECOND_ROW))
                        user.setBalance(user.getBalance() + (3 * b.getAmount()));
                    amount+= 3 * b.getAmount();
                }
            }
            else if (BetType.ROULETTE_THIRD_ROW== b.getType()){
                for (int i = 3; i <= 36; i += 3) {
                    if (i == winningNumber && b.getType().equals(BetType.ROULETTE_THIRD_ROW))
                        user.setBalance(user.getBalance() + (3 * b.getAmount()));
                    amount+= 3 * b.getAmount();
                }

            }

            if (winningNumber == 0 && b.getType() == BetType.ROULETTE_0){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }
            else if (winningNumber == 1 && b.getType() == BetType.ROULETTE_1){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 2 && b.getType() == BetType.ROULETTE_2){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 3 && b.getType() == BetType.ROULETTE_3){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 4 && b.getType() == BetType.ROULETTE_4){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 5 && b.getType() == BetType.ROULETTE_5){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 6 && b.getType() == BetType.ROULETTE_6){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 7 && b.getType() == BetType.ROULETTE_7) {
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount += 36 * b.getAmount();
            }
            else if (winningNumber == 8 && b.getType() == BetType.ROULETTE_8){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 9 && b.getType() == BetType.ROULETTE_9){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 10 && b.getType() == BetType.ROULETTE_10){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 11 && b.getType() == BetType.ROULETTE_11){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 12 && b.getType() == BetType.ROULETTE_12){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 13 && b.getType() == BetType.ROULETTE_13){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 14 && b.getType() == BetType.ROULETTE_14){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 15 && b.getType() == BetType.ROULETTE_15){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 16 && b.getType() == BetType.ROULETTE_16){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 17 && b.getType() == BetType.ROULETTE_17){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 18 && b.getType() == BetType.ROULETTE_18){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 19 && b.getType() == BetType.ROULETTE_19){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 20 && b.getType() == BetType.ROULETTE_20){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 21 && b.getType() == BetType.ROULETTE_21){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 22 && b.getType() == BetType.ROULETTE_22) {
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount += 36 * b.getAmount();
            }

            else if (winningNumber == 23 && b.getType() == BetType.ROULETTE_23){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();
            }

            else if (winningNumber == 24 && b.getType() == BetType.ROULETTE_24){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 25 && b.getType() == BetType.ROULETTE_25){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 26 && b.getType() == BetType.ROULETTE_26){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 27 && b.getType() == BetType.ROULETTE_27){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 28 && b.getType() == BetType.ROULETTE_28){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 29 && b.getType() == BetType.ROULETTE_29){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 30 && b.getType() == BetType.ROULETTE_30){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 31 && b.getType() == BetType.ROULETTE_31){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 32 && b.getType() == BetType.ROULETTE_32){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 33 && b.getType() == BetType.ROULETTE_33){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 34 && b.getType() == BetType.ROULETTE_34){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 35 && b.getType() == BetType.ROULETTE_35){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                amount+= 36 * b.getAmount();}

            else if (winningNumber == 36 && b.getType() == BetType.ROULETTE_36){
                user.setBalance(user.getBalance() + (36 * b.getAmount()));
                    amount+= 36 * b.getAmount();
                }
            }
        return amount;
    }

}


