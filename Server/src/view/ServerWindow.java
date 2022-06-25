package view;

import constants.ServerActionCommands;
import constants.Views;
import controller.ActionController;
import model.users.Guest;
import persistence.mysql.DatabaseManager;
import view.views.online_users.OnlineUsersView;
import view.views.online_users.UserBalanceHistoryView;
import view.views.rankings.RankingView;

import javax.swing.*;
import java.awt.*;

public class ServerWindow extends Window {
    private DatabaseManager databaseManager;
    private CardLayout cardLayout;
    private JPanel superiorPanel;
    private JPanel inferiorPanel;
    private JLabel optionsLabel;
    private JComboBox displayOptionsCombo;
    private OnlineUsersView onlineUsersView;
    private RankingView blackjackRankingView;
    private RankingView horseRaceRankingView;
    private RankingView rouletteRankingView;
    private UserBalanceHistoryView userBalanceHistoryView;

    private final String[] optionsTitle = {
            "Online Users", "Blackjack Ranking", "Horse Race Ranking", "Roulette Ranking"
    };

    public ServerWindow(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.render();
        this.configureWindow();
    }

    private void buildSuperiorPanel() {
        // Create superior panel
        this.superiorPanel = new JPanel();
        this.superiorPanel.setBorder(BorderFactory.createTitledBorder("Server Options"));

        // Create options label
        this.optionsLabel = new JLabel("Choose what do you want to display: ");

        // Create options combo box
        this.displayOptionsCombo = new JComboBox(this.optionsTitle);

        // Add components to superior panel
        this.superiorPanel.setLayout(new FlowLayout());
        this.superiorPanel.add(this.optionsLabel);
        this.superiorPanel.add(this.displayOptionsCombo);
    }

    private void buildInferiorPanel() {
        // Create inferior panel
        this.inferiorPanel = new JPanel();
        this.inferiorPanel.setBorder(BorderFactory.createTitledBorder("Display"));
        this.cardLayout = new CardLayout();
        this.inferiorPanel.setLayout(this.cardLayout);

        // Create views
        this.onlineUsersView = new OnlineUsersView();
        this.blackjackRankingView = new RankingView();
        this.horseRaceRankingView = new RankingView();
        this.rouletteRankingView = new RankingView();

        // Create secondary views from OnlineUsersView to card layout
        this.userBalanceHistoryView = new UserBalanceHistoryView();


        // Add views to card layout
        this.inferiorPanel.add(this.onlineUsersView, this.optionsTitle[0]);
        this.inferiorPanel.add(this.blackjackRankingView, this.optionsTitle[1]);
        this.inferiorPanel.add(this.horseRaceRankingView, this.optionsTitle[2]);
        this.inferiorPanel.add(this.rouletteRankingView, this.optionsTitle[3]);

        // Add secondary views from OnlineUsersView to card layout
        this.inferiorPanel.add(this.userBalanceHistoryView, Views.USER_BALANCE_HISTORY_VIEW);

    }

    private void render() {
        // Build panels
        this.buildSuperiorPanel();
        this.buildInferiorPanel();

        // Add panels to window
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(this.superiorPanel);
        this.add(this.inferiorPanel);
    }

    @Override
    protected void configureWindow() {
        super.configureWindow();
        this.setTitle("Casino - Server");
    }

    @Override
    public void addActionController(ActionController actionController) {
        this.displayOptionsCombo.setActionCommand(ServerActionCommands.DISPLAY_OPTIONS_COMBO_COMMAND);
        this.displayOptionsCombo.addActionListener(actionController);

        this.onlineUsersView.addActionController(actionController);
    }

    public void displayOptionFromCombo() {
        final int selectedIndex = this.displayOptionsCombo.getSelectedIndex();

        switch (selectedIndex) {
            case 1 -> blackjackRankingView.updateDataset(this.databaseManager.getRankingByGameType("blackjack"));
            case 2 -> horseRaceRankingView.updateDataset(this.databaseManager.getRankingByGameType("horses_race"));
            case 3 -> rouletteRankingView.updateDataset(this.databaseManager.getRankingByGameType("roulette"));
            default -> {
            }
        }

        this.cardLayout.show(this.inferiorPanel, this.optionsTitle[selectedIndex]);
    }

    public void displaySecondaryOnlineUserView(String secondaryViewID) {
        this.cardLayout.show(this.inferiorPanel, secondaryViewID);
    }

    public void addOnlineUser(Guest user) {
        this.onlineUsersView.addUser(user);
    }

    public void removeOnlineUser(Guest user) {
        this.onlineUsersView.removeUser(user);
    }

    public OnlineUsersView getOnlineUsersView() {
        return onlineUsersView;
    }

    public UserBalanceHistoryView getUserBalanceHistoryView() {
        return userBalanceHistoryView;
    }



    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}