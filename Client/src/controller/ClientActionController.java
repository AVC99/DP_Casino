package controller;

import constants.ClientActionCommands;
import constants.ViewIndexs;
import model.Bet;
import model.BetType;
import model.BlackJackResults;
import model.network.SocketActions;
import model.users.GameResult;
import model.users.UserSession;
import utils.JsonManager;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class ClientActionController extends ActionController implements WindowListener {
    private ClientController clientController;
    private Bet blackJackBet;


    public ClientActionController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ClientActionCommands.SIGN_IN_BUTTON_COMMAND, ClientActionCommands.SIGN_UP_BUTTON_COMMAND -> {
                String username = this.clientController.getWindow().getLoginView().getUsername();
                String password = this.clientController.getWindow().getLoginView().getPassword();
                UserSession userSession = new UserSession(username, password);

                // Check if login form is filled (both username and password)
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Action to execute on click SIGN_IN_BUTTON
                    if (e.getActionCommand().equals(ClientActionCommands.SIGN_IN_BUTTON_COMMAND)) {
                        this.clientController.sendSocketDataToServer(SocketActions.USER_ATTEMPT_SIGN_IN,
                                JsonManager.toJSON(userSession));
                    }

                    // Action to execute on click SIGN_UP_BUTTON
                    if (e.getActionCommand().equals(ClientActionCommands.SIGN_UP_BUTTON_COMMAND)) {
                        this.clientController.sendSocketDataToServer(SocketActions.USER_ATTEMPT_SIGN_UP,
                                JsonManager.toJSON(userSession));
                    }
                } else {
                    this.clientController.getWindow().showDialogNotification("FORM ERROR",
                            "You must fill all the fields", true);
                }
            }
            case ClientActionCommands.PLAY_AS_GUEST_BUTTON_COMMAND -> this.clientController.sendSocketDataToServer(SocketActions.USER_SIGNED_IN_AS_GUEST, "");
            case ClientActionCommands.LOGOUT_BUTTON_COMMAND -> {
                this.clientController.getWindow().displayView(ViewIndexs.LOGIN_VIEW_INDEX);
                this.clientController.logout(this.clientController.getUserManager().getUser());
            }
            case ClientActionCommands.BACK_BUTTON_COMMAND -> {
                this.clientController.getWindow().getHomeView().displayView(ViewIndexs.GAME_SELECTION_VIEW_INDEX);
                this.clientController.sendSocketDataToServer(SocketActions.USER_EXIT_LOBBY, JsonManager.toJSON(this.clientController.getUserManager().getUser()));
            }

            case ClientActionCommands.ADD_FUNDS_BUTTON_COMMAND -> {
                int inputNumber = this.clientController.getWindow().createInputDialog();
                this.clientController.addFunds(inputNumber);
            }

            case ClientActionCommands.HORSE_RACE_BUTTON_COMMAND -> {
                this.clientController.sendSocketDataToServer(SocketActions.ADD_USER_TO_HORSE_RACE_LOBBY,
                        JsonManager.toJSON(this.clientController.getUserManager().getUser()));

                this.clientController.getWindow().getHomeView().displayView(ViewIndexs.HORSE_RACE_VIEW_INDEX);
            }
            case ClientActionCommands.ROULETTE_BUTTON_COMMAND -> {
                this.clientController.sendSocketDataToServer(SocketActions.ADD_USER_TO_ROULETTE_LOBBY, JsonManager.toJSON
                        (this.clientController.getUserManager().getUser()));
                this.clientController.getWindow().getHomeView().displayView(ViewIndexs.ROULETTE_VIEW_INDEX);
            }


            case ClientActionCommands.NUMBER_INPUT_DIALOG_ACCEPT_BUTTON_COMMAND -> {

                //getNumberFromDialog(e);
            }
            case ClientActionCommands.HORSE_RACE_BET_PURPLE_BUTTON_COMMAND -> {
                this.clientController.addBet(this.clientController.getWindow().getHomeView().getHorseRaceView().addBet(BetType.HORSE_RACE_PURPLE));

            }
            case ClientActionCommands.HORSE_RACE_BET_RED_BUTTON_COMMAND -> {
                this.clientController.addBet(this.clientController.getWindow().getHomeView().getHorseRaceView().addBet(BetType.HORSE_RACE_RED));
            }
            case ClientActionCommands.HORSE_RACE_BET_BLUE_BUTTON_COMMAND -> {
                this.clientController.addBet(this.clientController.getWindow().getHomeView().getHorseRaceView().addBet(BetType.HORSE_RACE_BLUE));

            }


//Roulette action Commands-----------------------------------------------------------------------------------------------------------------
            case "0" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_0));
            case "1" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_1));
            case "2" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_2));
            case "3" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_3));
            case "4" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_4));
            case "5" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_5));
            case "6" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_6));
            case "7" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_7));
            case "8" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_8));
            case "9" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_9));
            case "10" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_10));
            case "11" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_11));
            case "12" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_12));
            case "13" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_13));
            case "14" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_14));
            case "15" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_15));
            case "16" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_16));
            case "17" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_17));
            case "18" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_18));
            case "19" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_19));
            case "20" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_20));
            case "21" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_21));
            case "22" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_22));
            case "23" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_23));
            case "24" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_24));
            case "25" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_25));
            case "26" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_26));
            case "27" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_27));
            case "28" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_28));
            case "29" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_29));
            case "30" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_30));
            case "31" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_31));
            case "32" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_32));
            case "33" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_33));
            case "34" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_34));
            case "35" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_35));
            case "36" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_36));
            case "First 12" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_FIRST_12));
            case "Second 12" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_SECOND_12));
            case "Third 12" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_THIRD_12));
            case "First row" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_FIRST_ROW));
            case "Second row" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_SECOND_ROW));
            case "Third row" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_THIRD_ROW));
            case "1-18" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_1_18));
            case "19-36" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_19_36));
            case "Even" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_EVEN));
            case "Odd" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_ODD));
            case "Red" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_RED));
            case "Black" -> this.clientController.addBet(this.clientController.getWindow().getHomeView().getRouletteView().addBet(BetType.ROULETTE_BLACK));

            //HOME VIEW ACTION ACTIONS----------------------------------------------------------------------------------------------------------------------

            case ClientActionCommands.BLACKJACK_BUTTON_COMMAND -> this.clientController.getWindow().getHomeView().displayView(ViewIndexs.BLACKJACK_VIEW_INDEX);
            case ClientActionCommands.BLACKJACK_START_BUTTON_COMMAND -> {
                this.clientController.getUserManager().getBetList().clear();
                blackJackBet = this.clientController.getWindow().getHomeView().getBlackjackView().addBet(BetType.BLACKJACK);
                this.clientController.addBet(blackJackBet);
                this.clientController.getWindow().getHomeView().getBlackjackView().startGame();
            }
            case ClientActionCommands.BLACKJACK_HIT_BUTTON_COMMAND -> {
                BlackJackResults results = this.clientController.getWindow().getHomeView().getBlackjackView().hit();

                if (results!=BlackJackResults.CONTINUE){
                    System.out.println("BlackJackResults: " + results);
                    int amount = this.clientController.getUserManager().payBlackJackBet(results);
                    boolean isVictory = results == BlackJackResults.PLAYER_WIN;

                    this.clientController.getWindow().getHomeView().setPlayerInformation(this.clientController.getUserManager().getUser());
                    this.clientController.updateDatabase(new GameResult(amount, "BlackJack", isVictory,
                            this.clientController.getUserManager().getUser().getUsername(), this.clientController.getUserManager().getUser()));
                    this.clientController.getWindow().getHomeView().getBlackjackView().showResultPane(results,amount);
                }
            }
            case ClientActionCommands.BLACKJACK_DOUBLE_BUTTON_COMMAND -> {
                this.clientController.addBet(blackJackBet);
                this.clientController.getWindow().getHomeView().getBlackjackView().doubleBet();
            }
            case ClientActionCommands.BLACKJACK_STAY_BUTTON_COMMAND -> {
                BlackJackResults results = this.clientController.getWindow().getHomeView().getBlackjackView().stay();
                int amount = this.clientController.getUserManager().payBlackJackBet(results);
                boolean isVictory = results == BlackJackResults.PLAYER_WIN;


                this.clientController.getWindow().getHomeView().setPlayerInformation(this.clientController.getUserManager().getUser());
                this.clientController.updateDatabase(new GameResult(amount, "BlackJack", isVictory,
                        this.clientController.getUserManager().getUser().getUsername(), this.clientController.getUserManager().getUser()));
                System.out.println(this.clientController.getUserManager().getUser().getBalance());

                this.clientController.getWindow().getHomeView().getBlackjackView().showResultPane(results,amount);



            }
        }
    }
   /* private int getNumberFromDialog(ActionEvent e) {
        boolean isDigit;
        int inputNumber = 0;

        try {
            inputNumber = Integer.parseInt(this.clientController.getWindow().getNumberInputDialog().
                    getInputNumber());
            isDigit = true;
        } catch (NumberFormatException exception) {
            isDigit = false;
        }

        // Check if input is an integer
        if (isDigit ) {
            switch (this.clientController.getWindow().getNumberInputDialog().getContext()) {

                case ADD_FUNDS -> {

                        this.clientController.addFunds(inputNumber);
                        this.clientController.sendSocketDataToServer(SocketActions.USER_ADDED_FUNDS,
                                JsonManager.toJSON(this.clientController.getUserManager().getUser()));


                }
                case SET_BET -> {
                    // TODO Set bet amount
                }
            }
        } else {
            this.clientController.getWindow().getNumberInputDialog().
                    showDialogNotification("INVALID INPUT", "You must enter a number", true);
        }
        return 0;
    }*/

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Window closing");
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

        }
}

