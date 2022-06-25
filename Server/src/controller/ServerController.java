package controller;

import model.DedicatedServer;
import model.HorseRaceLobby;
import model.RouletteLobby;
import persistence.mysql.DatabaseManager;
import view.ServerWindow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerController {
    private ServerWindow window;
    private ServerSocket server;
    private DatabaseManager databaseManager;
    private ArrayList<DedicatedServer> dedicatedServers;
    private RouletteLobby rouletteLobby;
    private HorseRaceLobby horseRaceLobby;
    private int rouletteUsersCount;
    private int horseRaceUsersCount;

    public ServerController(ServerWindow window, ServerSocket server, DatabaseManager databaseManager) {
        this.rouletteUsersCount = 0;
        this.horseRaceUsersCount=0;
        this.window = window;
        this.server = server;
        this.databaseManager = databaseManager;
        this.dedicatedServers = new ArrayList<>();
        this.rouletteLobby= new RouletteLobby();
        this.horseRaceLobby = new HorseRaceLobby();


        this.renderWindow();
    }
    public void startRouletteLobby(){
        if (this.rouletteUsersCount == 0) {
            new Thread(rouletteLobby).start();
        }
        rouletteUsersCount++;
    }
    public void startHorseRaceLobby(){
        if (horseRaceUsersCount==0){
            new Thread(horseRaceLobby).start();
        }
        horseRaceUsersCount++;
    }
    public void removeUserFromRouletteLobby(){
        rouletteUsersCount--;
    }
    public void removeUserFromHorseRaceLobby(){
        horseRaceUsersCount--;
    }

    private void renderWindow() {
        this.window.setTitle("Casino - Server");
        this.window.setVisible(true);
    }

    public void startServer() {
        System.out.println("Server listening...");

        // Constantly wait for new clients
        while (true) {

            try {
                // Accept a new client (blocking)
                Socket client = this.server.accept();

                System.out.println("A new client has connected to the server");

                // Start a dedicated server (Thread) for that client, adding it to the list
                DedicatedServer dedicatedServer = new DedicatedServer(this.window, client, this.dedicatedServers,
                        this.databaseManager, this);
                dedicatedServers.add(dedicatedServer);
                new Thread(dedicatedServer).start();
            } catch (IOException e) {
                this.databaseManager.closeConnection();
                this.closeSocket();
                e.printStackTrace();
            }
        }
    }


    public RouletteLobby getRouletteLobby() {
        return rouletteLobby;
    }

    public HorseRaceLobby getHorseRaceLobby() {
        return horseRaceLobby;
    }

    private void closeSocket() {
        try {
            this.server.close();
        } catch (IOException e) {
            System.out.println("ERROR: Server port could not be closed");
        }
    }

}
