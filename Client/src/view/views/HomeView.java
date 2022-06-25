package view.views;

import constants.ClientActionCommands;
import constants.ViewIndexs;
import controller.ActionController;
import model.users.Guest;
import view.View;
import view.components.PlayerInformationComponent;
import view.views.blackjack.BlackjackView;

import javax.swing.*;
import java.awt.*;

public class HomeView extends View {
    private static final String[] CARDS = {"gameSelection", "horseRace", "roulette", "blackJack"};
    public static final int HEIGHT_USER_PANEL = 75;
    private JButton logoutButton;
    private JButton backButton;
    private CardLayout cardLayout;
    private JPanel centerPanel;
    private JPanel northPanel;
    private GameSelectionView gameSelectionView;
    private HorseRaceView horseRaceView;
    private BlackjackView blackjackView;
    private RouletteView rouletteView;
    private PlayerInformationComponent playerInformationComponent;
    private Guest user;
    private ActionController actionController;

    public HomeView() {
        this.render();
        this.configureNorth();
        this.configureCenter();
    }

    private void configureCenter() {
        this.centerPanel = new JPanel();
        this.cardLayout = new CardLayout();
        this.centerPanel.setLayout(cardLayout);
        this.gameSelectionView = new GameSelectionView();

        this.centerPanel.add(this.gameSelectionView, CARDS[0]);
        this.horseRaceView = new HorseRaceView();
        this.centerPanel.add(this.horseRaceView, CARDS[1]);
        this.rouletteView = new RouletteView();
        this.centerPanel.add(this.rouletteView, CARDS[2]);
        this.blackjackView = new BlackjackView();
        this.centerPanel.add(this.blackjackView, CARDS[3]);

        this.add(this.centerPanel);
    }

    private void render() {
        this.setLayout(new BorderLayout());
    }

    private void configureNorth() {
        this.northPanel = new JPanel();
        this.northPanel.setLayout(new BorderLayout());

        // Create buttons
        this.logoutButton = new JButton("Logout");
        this.backButton = new JButton("Back");
        this.backButton.setVisible(false);

        // Add buttons for user actions on a panel
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.add(this.backButton, BorderLayout.WEST);
        buttons.add(this.logoutButton, BorderLayout.EAST);

        // Add buttons to view
        this.add(buttons, BorderLayout.EAST);

        // Add components to north panel of the view
        this.northPanel.add(buttons, BorderLayout.EAST);
        this.northPanel.setPreferredSize(new Dimension(0, HEIGHT_USER_PANEL));
        this.add(this.northPanel, BorderLayout.NORTH);
    }

    @Override
    public void addActionController(ActionController actionController) {
        this.actionController = actionController;

        this.logoutButton.addActionListener(actionController);
        this.logoutButton.setActionCommand(ClientActionCommands.LOGOUT_BUTTON_COMMAND);

        this.backButton.addActionListener(actionController);
        this.backButton.setActionCommand(ClientActionCommands.BACK_BUTTON_COMMAND);

        this.gameSelectionView.addActionController(actionController);
        this.rouletteView.addActionController(actionController);
        this.horseRaceView.addActionController(actionController);
        this.blackjackView.addActionController(actionController);
    }

    public void displayView(int viewIndex) {
        this.cardLayout.show(this.centerPanel, CARDS[viewIndex]);

        // Set buttons visibility depending on the view to display
        if (ViewIndexs.GAME_SELECTION_VIEW_INDEX == viewIndex) {
            this.setLogoutButtonVisibility(true);
            this.setBackButtonVisibility(false);
        } else {
            this.setLogoutButtonVisibility(false);
            this.setBackButtonVisibility(true);
        }
    }

    public void setLogoutButtonVisibility(boolean state) {
        this.logoutButton.setVisible(state);
    }


    public void setBackButtonVisibility(boolean state) {
        this.backButton.setVisible(state);
    }

    public RouletteView getRouletteView() {
        return rouletteView;
    }

    public HorseRaceView getHorseRaceView() {
        return horseRaceView;
    }


    public BlackjackView getBlackjackView() {
        return blackjackView;
    }

    public void setPlayerInformation(Guest user) {
        this.user = user;

        // Create PlayerInformationComponent
        if (this.playerInformationComponent == null) {
            this.playerInformationComponent = new PlayerInformationComponent(this.user);

            // Add component to north panel of the view
            this.northPanel.add(this.playerInformationComponent, BorderLayout.WEST);

            // Pass the ActionController to the component
            this.playerInformationComponent.addActionController(this.actionController);
        } else {
            // Update component information
            this.playerInformationComponent.updateUsername(user.getUsername());
            this.playerInformationComponent.updateBalance(user.getBalance());
            this.revalidate();
            this.repaint();
        }
    }
}
