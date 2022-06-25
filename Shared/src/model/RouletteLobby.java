package model;


import model.network.SocketActions;
import model.network.SocketData;
import utils.JsonManager;

import java.util.ArrayList;
import java.util.Random;

public class RouletteLobby implements  Runnable{
    private ArrayList<DedicatedServer> dedicatedServers;
    private boolean canStart;

    public RouletteLobby() {
        this.dedicatedServers = new ArrayList<>();
        canStart=false;
    }

    public int getWinningNumber() {
        Random random = new Random();
        return random.nextInt(37);
    }

    private void sendWinningNumberBroadcast(){
        int winningNumber = getWinningNumber();
        for (DedicatedServer dedicatedServer : this.dedicatedServers) {
            dedicatedServer.sendMessage(new SocketData(SocketActions.WINNING_ROULETTE_NUMBER_BROADCAST,
                    JsonManager.toJSON(winningNumber)));
            System.out.println("Sending winning number to server: " + winningNumber);

        }
    }

    public boolean removeFromLobby(DedicatedServer dedicatedServer){
        return dedicatedServers.removeIf(d -> d == dedicatedServer);
    }

    public void addDedicatedServer(DedicatedServer dedicatedServer) {
        this.dedicatedServers.add(dedicatedServer);
        System.out.println("Roulette lobby starting ");
        canStart=true;

    }

    @Override
    public void run() {
        while (canStart==true) {
            try {
                Thread.sleep(5000);
                sendOpenBetsBroadcast();

                Thread.sleep(10000);
                //Close bets broadcast
                sendCloseBetsBroadcast();
                Thread.sleep(10000);
                //add sleep
                sendWinningNumberBroadcast();
                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendCloseBetsBroadcast() {
        for (DedicatedServer dedicatedServer : this.dedicatedServers) {
            dedicatedServer.sendMessage(new SocketData(SocketActions.CLOSE_ROULETTE_BET_BROADCAST,""));
            System.out.println("Roulette lobby sending close bets broadcast");

        }

    }

    private void sendOpenBetsBroadcast() {
        for (DedicatedServer dedicatedServer : this.dedicatedServers) {
            dedicatedServer.sendMessage(new SocketData(SocketActions.OPEN_ROULETTE_BET_BROADCAST,""));
            System.out.println("Roulette lobby sending open bets broadcast");

        }


    }
}
