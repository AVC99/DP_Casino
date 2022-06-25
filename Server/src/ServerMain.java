import controller.ServerActionController;
import controller.ServerController;
import persistence.ServerConfigurationDAO;
import persistence.mysql.DatabaseManager;
import view.ServerWindow;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) {
        // Read server's configuration file
        ServerConfigurationDAO serverConfigurationDAO = new ServerConfigurationDAO();
        try {
            // Create a ServerSocket
            ServerSocket server = new ServerSocket(serverConfigurationDAO.getPort());

            // Create a DatabaseManager
            DatabaseManager databaseManager = new DatabaseManager(serverConfigurationDAO);

            // Create a Window for the server
            ServerWindow window = new ServerWindow(databaseManager);
            window.addActionController(new ServerActionController(window));

            // Create a ServerController

            ServerController serverController = new ServerController(window, server, databaseManager);
            serverController.startServer();
        } catch (IOException e) {
            System.out.println("ERROR: Server failed to initialize properly.");
        }
    }
}
