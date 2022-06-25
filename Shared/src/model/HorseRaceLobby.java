package model;

import constants.ResourcesPath;
import model.network.SocketActions;
import model.network.SocketData;
import utils.JsonManager;

import java.util.ArrayList;

public class HorseRaceLobby implements Runnable{
    private ArrayList<DedicatedServer> dedicatedServers;
    private ArrayList<Horse> horseList;
    boolean canStart;

    public HorseRaceLobby() {
        this.dedicatedServers = new ArrayList<>();
        this.horseList= new ArrayList<>();
        this.canStart=false;
    }

    private void initializeHorses() {
        int horseY=200;
        int i=0;
        for(String s: ResourcesPath.HORSE_PATHS ){
            horseList.add(new Horse(s,horseY,i));
            i++;
            horseY+=50;

        }
    }

    public boolean removeFromLobby(DedicatedServer dedicatedServer){
        return dedicatedServers.removeIf(d -> d == dedicatedServer);
    }

    private Horse getWinningHorse(){
        int winningSpeed=0;
        Horse winningHorse=null;
        for(Horse h: this.horseList){
            if(h.getHorseSpeed()>winningSpeed){
                winningSpeed=h.getHorseSpeed();
                winningHorse=h;
            }
        }
        return winningHorse;
    }

    public void run() {
        while (canStart) {
            try {
                sendOpenBetsBroadcast();
                Thread.sleep(10000);
                //Close bets broadcast
                this.horseList.clear();
                initializeHorses();
                sendCloseBetsBroadcast();
                Thread.sleep(10000);
                sendRunHorsesBroadcast();
                Thread.sleep(20000);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRunHorsesBroadcast() {
        for (DedicatedServer dedicatedServer : this.dedicatedServers) {
            dedicatedServer.sendMessage(new SocketData(SocketActions.START_HORSE_RACE_BROADCAST, JsonManager.toJSON(this.horseList)));
            System.out.println("Sending run and horselist");
        }
    }

    public void addDedicatedServer(DedicatedServer dedicatedServer) {
        this.dedicatedServers.add(dedicatedServer);
        canStart=true;
    }

    private void sendCloseBetsBroadcast() {
       Horse winningHorse= getWinningHorse();
        for (DedicatedServer dedicatedServer : this.dedicatedServers) {
            dedicatedServer.sendMessage(new SocketData(SocketActions.CLOSE_HORSERACE_BET_BROADCAST,JsonManager.toJSON(winningHorse)));
        }

    }

    private void sendOpenBetsBroadcast() {
        for (DedicatedServer dedicatedServer : this.dedicatedServers) {
            dedicatedServer.sendMessage(new SocketData(SocketActions.OPEN_HORSERACE_BET_BROADCAST,JsonManager.toJSON(this.horseList)));
            System.out.println("Sending  open bet broadcast");

        }


    }
}
