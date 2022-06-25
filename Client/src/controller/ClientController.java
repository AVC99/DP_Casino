package controller;

import constants.ViewIndexs;
import model.Bet;
import model.network.SocketActions;
import model.network.SocketData;
import model.users.GameResult;
import model.users.Guest;
import model.users.User;
import model.users.UserManager;
import utils.JsonManager;
import view.ClientWindow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController {
    private final ClientWindow window;
    private final Socket socket;
    private UserManager userManager;

    public ClientController(ClientWindow window, Socket socket) {
        this.window = window;
        this.socket = socket;
        this.window.setVisible(true);
    }

    public void sendSocketDataToServer(SocketActions action, String data) {
        try {
            SocketData socketData = new SocketData(action, data);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectOutputStream.writeObject(socketData);
        } catch (IOException e) {
            System.out.println("ERROR: Client cannot send a " + action + " socket to the server.");
        }
    }

    public ObjectInputStream readSocketDataFromServer() throws IOException {
        return new ObjectInputStream(this.socket.getInputStream());
    }

    public ClientWindow getWindow() {
        return window;
    }

    public void navigateToView(int viewIndex) {
        if (viewIndex == ViewIndexs.HOME_VIEW_INDEX) {
            this.getWindow().getHomeView().setPlayerInformation(this.userManager.getUser());
        }

        this.window.displayView(viewIndex);
    }

    public void createUser(Guest user) {
        this.userManager = new UserManager(user);
        this.window.getHomeView().setPlayerInformation(user);
    }

    public void logout(Guest user) {
        this.sendSocketDataToServer(SocketActions.USER_LOGOUT, JsonManager.toJSON(user));
        this.userManager.logout();
    }

    public void addFunds(int number) {
        // Add funds to user
        this.userManager.addFunds(number);

        // Send data to server for updating database if user is not a guest
        if (this.userManager.getUser() instanceof User) {
            this.sendSocketDataToServer(SocketActions.USER_ADDED_FUNDS, JsonManager.toJSON(this.userManager.getUser()));
        }

        // Update balance counter
        this.window.getHomeView().setPlayerInformation(this.userManager.getUser());
    }

    public void addBet(Bet bet) {
        //add bet to the user manager and to the bet list fo the user
        userManager.addBet(bet);

        //updates the player information
        this.window.getHomeView().setPlayerInformation(this.userManager.getUser());
    }


    public void updateDatabase(GameResult gameResult) {
        // Update database
        this.sendSocketDataToServer(SocketActions.UPDATE_DATABASE, JsonManager.toJSON(gameResult));
    }


    public UserManager getUserManager() {
        return userManager;
    }

    public Socket getSocket() {
        return socket;
    }
}
