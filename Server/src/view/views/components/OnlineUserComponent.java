package view.views.components;

import constants.ServerActionCommands;
import controller.ActionController;
import model.users.Guest;
import model.users.User;
import view.View;

import javax.swing.*;
import java.awt.*;

public class OnlineUserComponent extends View {
    private Guest user;
    private final JLabel username;

    private JButton balanceHistoryButton;

    public OnlineUserComponent(Guest user, ActionController actionController) {
        this.user = user;
        this.username = this.buildUsernameLabel(this.user.getUsername());

        if(user instanceof User) {
            this.balanceHistoryButton = this.buildBalanceHistoryButton();
        }

        this.render(user);
        this.addActionController(actionController);
    }

    private JLabel buildUsernameLabel(String username) {
        return new JLabel(username);
    }

    private JButton buildGamesHistoryButton() {
        return new JButton("Match History");
    }

    private JButton buildBalanceHistoryButton() {
        return new JButton("Balance History");
    }

    private void render(Guest user) {
        // Configure layout
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createRaisedBevelBorder());

        // Add username label
        this.add(this.username, BorderLayout.CENTER);

        // Check if user is not a guest
        if(user instanceof User) {
            // Add buttons for user actions on a panel
            JPanel buttons = new JPanel();
            buttons.setLayout(new BorderLayout());
            buttons.add(this.balanceHistoryButton, BorderLayout.WEST);


            // Add buttons to component view
            this.add(buttons, BorderLayout.EAST);
        }

        this.setPreferredSize(new Dimension(0, 50));
    }

    @Override
    public void addActionController(ActionController actionController) {
        if(this.balanceHistoryButton != null) {
            this.balanceHistoryButton.setActionCommand(ServerActionCommands.BALANCE_HISTORY_BUTTON_COMMAND + "_" +
                    this.getUser().getUsername());
            this.balanceHistoryButton.addActionListener(actionController);
        }
    }

    public Guest getUser() {
        return user;
    }
}
