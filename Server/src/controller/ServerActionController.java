package controller;

import constants.Views;
import model.users.User;
import view.ServerWindow;
import view.views.components.OnlineUserComponent;

import java.awt.event.ActionEvent;

import static constants.ServerActionCommands.BALANCE_HISTORY_BUTTON_COMMAND;
import static constants.ServerActionCommands.DISPLAY_OPTIONS_COMBO_COMMAND;

public class ServerActionController extends ActionController {
    private final ServerWindow window;

    public ServerActionController(ServerWindow window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case DISPLAY_OPTIONS_COMBO_COMMAND -> this.window.displayOptionFromCombo();
            default -> this.detectOnlineUserViewButton(e.getActionCommand());
        }
    }

    private void detectOnlineUserViewButton(String actionCommand) {
        // Get from which online user was pressed
        String username = actionCommand.split("_")[actionCommand.split("_").length - 1];

        // Get user associated to the clicked OnlineUserComponent
        User user = null;

        for(OnlineUserComponent component : this.window.getOnlineUsersView().getOnlineUserComponents()) {
            if(component.getUser().getUsername().equals(username)) {
                user = (User) component.getUser();
            }
        }

        // Check which button was pressed
        if(actionCommand.contains(BALANCE_HISTORY_BUTTON_COMMAND)) {
            // Show BalanceHistoryView
            this.window.getUserBalanceHistoryView().setUser(user);
            this.window.getUserBalanceHistoryView().setDatabaseManager(this.window.getDatabaseManager());
            this.window.displaySecondaryOnlineUserView(Views.USER_BALANCE_HISTORY_VIEW);
        }
    }
}
