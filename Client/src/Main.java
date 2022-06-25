import controller.ClientActionController;
import controller.ClientController;
import controller.ClientWindowController;
import model.RealTimeListener;
import persistence.ClientConfigurationDAO;
import view.ClientWindow;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // Read client's configuration file
        ClientConfigurationDAO clientConfigurationDAO = new ClientConfigurationDAO();

        // Open the connection with the server
        try {
            Socket server = new Socket(clientConfigurationDAO.getServerIP(), clientConfigurationDAO.getServerPort());

            // Create a Window for the client
            ClientWindow window = new ClientWindow();

            // Pass Window and Socket to ClientController
            ClientController clientController = new ClientController(window, server);

            // Add ClientActionController to the Window
            window.addActionController(new ClientActionController(clientController));

            // Add WindowListener to the Window
            window.addWindowListener(new ClientWindowController(clientController));

            RealTimeListener realTimeListener = new RealTimeListener(clientController);
            new Thread(realTimeListener).start();
        } catch (IOException e) {
            System.out.println("ERROR: Client cannot connect to the the server.");
        }
    }
}
