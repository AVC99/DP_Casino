package model;

import constants.ViewIndexs;
import controller.ClientController;
import model.network.SocketData;
import model.users.GameResult;
import utils.JsonManager;

import javax.swing.*;
import java.io.IOException;

public class RealTimeListener implements Runnable {
    private final ClientController clientController;
    private Boolean exit;
    private boolean isVictory;
    private int amount;

    public RealTimeListener(ClientController clientController) {
        this.clientController = clientController;
        this.exit = false;
    }

    @Override
    public void run() {
        // Read messages until we want to stop / an exception is thrown
        while (!exit) {
            try {
                SocketData socketData = (SocketData) this.clientController.readSocketDataFromServer().readObject();

                /* Manage every broadcast received from server */
                switch (socketData.getAction()) {
                    case USER_SIGNED_IN_SUCCESSFULLY -> SwingUtilities.invokeLater(() -> {
                        this.clientController.createUser(JsonManager.getUserFromJson(socketData.getData()));
                        this.clientController.navigateToView(ViewIndexs.HOME_VIEW_INDEX);
                    });
                    case USER_SIGNED_IN_ERROR -> this.clientController.getWindow().showDialogNotification("LOGIN ERROR",
                            "Username or password are not valid", true);
                    case USER_SIGNED_UP_SUCCESSFULLY -> this.clientController.getWindow().showDialogNotification("SIGNED UP SUCCESSFUL",
                            "User has been created successfully", false);
                    case USER_SIGNED_UP_ERROR -> this.clientController.getWindow().showDialogNotification("SIGNED UP ERROR",
                            "User has not been created. Try changing your username!", true);
                    case USER_SIGNED_IN_AS_GUEST_SUCCESSFULLY -> {
                        this.clientController.createUser(JsonManager.getGuestFromJson(socketData.getData()));
                        SwingUtilities.invokeLater(() ->
                                this.clientController.navigateToView(ViewIndexs.HOME_VIEW_INDEX));
                    }
                    case WINNING_ROULETTE_NUMBER_BROADCAST -> SwingUtilities.invokeLater(() -> {
                        this.clientController.getWindow().getHomeView().getRouletteView().setWinningNumber(
                                Integer.parseInt(socketData.getData()));
                        this.clientController.getWindow().getHomeView().getRouletteView().stopSpinning();

                        int amount=this.clientController.getUserManager().payRouletteBet(Integer.parseInt(socketData.getData()),
                                this.clientController.getWindow().getHomeView().getRouletteView().getRoulette().getRouletteCells());

                        if (amount>0)isVictory=true;
                        else isVictory=false;
                        System.out.println("amount: "+amount);
                        this.clientController.getWindow().getHomeView().setPlayerInformation(this.clientController.getUserManager().getUser());
                        this.clientController.updateDatabase(new GameResult(amount,"Roulette", isVictory,
                                this.clientController.getUserManager().getUser().getUsername(),this.clientController.getUserManager().getUser()));
                    });

                    case OPEN_ROULETTE_BET_BROADCAST -> SwingUtilities.invokeLater(() -> {
                        this.clientController.getWindow().getHomeView().getRouletteView().getRouletteBoard().toggleButtons(true);
                        this.clientController.getUserManager().getBetList().clear();
                        this.clientController.getWindow().getHomeView().getRouletteView().spin();

                    });

                    case CLOSE_ROULETTE_BET_BROADCAST -> SwingUtilities.invokeLater(() -> {
                        this.clientController.getWindow().getHomeView().getRouletteView().getRouletteBoard().toggleButtons(false);

                    });
                    case START_HORSE_RACE_BROADCAST -> SwingUtilities.invokeLater(() -> {
                                this.clientController.getWindow().getHomeView().getHorseRaceView().getHorseRaceField().
                                        getHorseList().clear();
                                this.clientController.getWindow().getHomeView().getHorseRaceView().getHorseRaceField().
                                        setHorseList(JsonManager.getHorsesFromJson(socketData.getData()));
                                this.clientController.getWindow().getHomeView().getHorseRaceView().startRace();
                                this.clientController.getWindow().getHomeView().getHorseRaceView().informResult(amount, isVictory);
                            }
                    );
                    case OPEN_HORSERACE_BET_BROADCAST -> SwingUtilities.invokeLater(() -> {
                        this.clientController.getUserManager().getBetList().clear();
                        this.clientController.getWindow().getHomeView().getHorseRaceView().getHorseRaceField().
                                getHorseList().clear();
                        this.clientController.getWindow().getHomeView().getHorseRaceView().toggleBetButtons(true);
                        this.clientController.getWindow().getHomeView().getHorseRaceView().getHorseRaceField().
                                setHorseList(JsonManager.getHorsesFromJson(socketData.getData()));
                        this.clientController.getWindow().getHomeView().getHorseRaceView().getHorseRaceField().
                                stopRunning();
                        this.clientController.getUserManager().getBetList().clear();
                    });
                    case CLOSE_HORSERACE_BET_BROADCAST -> SwingUtilities.invokeLater(() -> {
                        this.clientController.getWindow().getHomeView().getHorseRaceView().toggleBetButtons(false);
                      amount  =this.clientController.getUserManager().payHorseBet(JsonManager.getHorseFromJson(socketData.getData()));
                        System.out.println(amount);
                         isVictory = amount > 0;
                        this.clientController.getWindow().getHomeView().setPlayerInformation(this.clientController.getUserManager().getUser());
                        this.clientController.updateDatabase(new GameResult(amount, "HorseRace",isVictory,
                                this.clientController.getUserManager().getUser().getUsername(), this.clientController.getUserManager().getUser()));

                    });



                }
            } catch (IOException | ClassNotFoundException e) {
                exit = true;
            }
        }
    }
}
