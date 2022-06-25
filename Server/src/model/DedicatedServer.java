package model;

import controller.ServerController;
import model.network.SocketActions;
import model.network.SocketData;
import model.users.Guest;
import model.users.UserSession;
import persistence.mysql.DatabaseManager;
import utils.JsonManager;
import view.ServerWindow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class DedicatedServer implements Runnable {
    private Guest user;
    private final ServerWindow serverWindow;
    private final Socket socket;
    private final ArrayList<DedicatedServer> servers;
    private DatabaseManager databaseManager;
    private ServerController serverController;

    public DedicatedServer(ServerWindow window, Socket socket, ArrayList<DedicatedServer> dedicatedServers,
                           DatabaseManager databaseManager, ServerController serverController) {
        this.serverWindow = window;
        this.socket = socket;
        this.servers = dedicatedServers;
        this.databaseManager = databaseManager;
        this.serverController = serverController;
    }

    @Override
    public void run() {
        try {
            SocketData socketData;

            // Permanently read the next message the user sends
            do {
                socketData = (SocketData) new ObjectInputStream(this.socket.getInputStream()).readObject();

                // Check for the action received
                switch (socketData.getAction()) {
                    case USER_ATTEMPT_SIGN_IN, USER_ATTEMPT_SIGN_UP -> {
                        UserSession userSession = JsonManager.getUserSessionFromJson(socketData.getData());

                        // Action to execute on receive a USER_ATTEMPT_SIGN_IN
                        if (socketData.getAction().equals(SocketActions.USER_ATTEMPT_SIGN_IN)) {
                            if (this.databaseManager.authenticateCredentials(userSession)) {
                                this.user = this.databaseManager.getUser(userSession.getUsername());
                                this.sendMessage(new SocketData(SocketActions.USER_SIGNED_IN_SUCCESSFULLY,
                                        JsonManager.toJSON(this.user)));
                                this.serverWindow.addOnlineUser(this.user);

                            } else {
                                this.sendMessage(new SocketData(SocketActions.USER_SIGNED_IN_ERROR, ""));
                            }
                        }

                        // Action to execute on receive a USER_ATTEMPT_SIGN_UP
                        if (socketData.getAction().equals(SocketActions.USER_ATTEMPT_SIGN_UP)) {
                            if (this.databaseManager.registerCredentials(userSession)) {
                                this.sendMessage(new SocketData(SocketActions.USER_SIGNED_UP_SUCCESSFULLY, ""));
                            } else {
                                this.sendMessage(new SocketData(SocketActions.USER_SIGNED_UP_ERROR, ""));
                            }
                        }
                    }
                    case USER_SIGNED_IN_AS_GUEST -> {
                        this.user = new Guest();
                        SocketData response = new SocketData(SocketActions.USER_SIGNED_IN_AS_GUEST_SUCCESSFULLY,
                                JsonManager.toJSON(this.user));
                        this.sendMessage(response);
                        this.serverWindow.addOnlineUser(this.user);
                    }
                    case USER_ADDED_FUNDS -> {

                        this.user = JsonManager.getUserFromJson(socketData.getData());

                        this.databaseManager.addFunds(this.user);

                        this.databaseManager.addFundsToBalanceHistory(this.user);

                    }
                    case USER_LOGOUT -> this.serverWindow.removeOnlineUser(this.user);

                    case ADD_USER_TO_ROULETTE_LOBBY -> {
                        this.user=JsonManager.getUserFromJson(socketData.getData());
                        System.out.println("user added to roulette lobby");
                        this.serverController.getRouletteLobby().addDedicatedServer(this);
                        this.serverController.startRouletteLobby();
                    }
                    case ADD_USER_TO_HORSE_RACE_LOBBY -> {
                        this.user=JsonManager.getUserFromJson(socketData.getData());
                        System.out.println("User added to horse race lobby");
                        this.serverController.getHorseRaceLobby().addDedicatedServer(this);
                        this.serverController.startHorseRaceLobby();
                    }
                    case USER_EXIT_LOBBY ->{
                       if(this.serverController.getHorseRaceLobby().removeFromLobby(this))this.serverController.removeUserFromHorseRaceLobby();
                       if(this.serverController.getRouletteLobby().removeFromLobby(this))this.serverController.removeUserFromRouletteLobby();
                    }

                    case USER_DISCONNECTED -> {
                        // Disconnect
                        this.serverWindow.removeOnlineUser(this.user);
                        this.socket.close();
                    }
                    case UPDATE_DATABASE -> {
                        this.databaseManager.updateDatabase(JsonManager.getGameResultFromJson(socketData.getData()));
                    }

                }
            } while (!socketData.getAction().equals(SocketActions.USER_DISCONNECTED));
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        // Remove the dedicated server from the list and close the socket
        this.servers.remove(this);
        try {
            this.socket.close();
        } catch (IOException exception) {
            System.out.println("ERROR: Player " + this.user.getUsername() +
                    " could not close socket communication properly");
        }
    }

    private void broadcastToOthers(SocketData socketData) {
        for (DedicatedServer dedicatedServer : servers) {
            if (dedicatedServer != this) {
                dedicatedServer.sendMessage(socketData);
            }
        }
    }

    public void sendMessage(SocketData socketData) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(socketData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
