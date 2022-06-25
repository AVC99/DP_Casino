package view.components;

import constants.ClientActionCommands;
import controller.ActionController;
import model.users.Guest;
import view.View;

import javax.swing.*;
import java.awt.*;

public class PlayerInformationComponent extends View {
    private Guest user;
    private JLabel username;
    private JLabel balance;
    private JButton addFundsButton;

    public PlayerInformationComponent(Guest user) {
        this.user = user;

        this.render();
    }

    private void render() {
        // Configure layout
        GridLayout layout = new GridLayout(1, 2);
        GridLayout layoutWrapper = new GridLayout(1, 2);
        layout.setHgap(100);
        this.setLayout(layout);

        // Create components
        this.username = new JLabel(this.user.getUsername());
        JPanel wrapper = new JPanel(layoutWrapper);
        this.balance = new JLabel(String.valueOf(this.user.getBalance()));
        this.addFundsButton = new JButton("+");

        // Add components to view
        this.add(this.username);
        wrapper.add(this.balance);
        wrapper.add(this.addFundsButton);
        this.add(wrapper);
    }

    @Override
    public void addActionController(ActionController actionController) {
        this.addFundsButton.setActionCommand(ClientActionCommands.ADD_FUNDS_BUTTON_COMMAND);
        this.addFundsButton.addActionListener(actionController);
    }

    public void updateUsername(String username) {
        this.username.setText(username);
        this.revalidate();
        this.repaint();
    }

    public void updateBalance(int balance) {
        this.balance.setText(String.valueOf(balance));
        this.revalidate();
        this.repaint();
    }
}
