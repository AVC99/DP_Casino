package controller;

import model.network.SocketActions;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class ClientWindowController implements WindowListener {
    private final ClientController clientController;

    public ClientWindowController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.clientController.sendSocketDataToServer(SocketActions.USER_DISCONNECTED, "");
        try {
            this.clientController.getSocket().close();
        } catch (IOException ex) {
            System.out.println("ERROR: Socket could not be closed properly");
        }
        e.getWindow().dispose();
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
