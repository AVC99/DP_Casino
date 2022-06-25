package view.views;

import constants.ClientActionCommands;
import constants.ResourcesPath;
import controller.ActionController;
import view.View;

import javax.swing.*;
import java.awt.*;

public class GameSelectionView extends View {
    private JButton horseRaceButton;
    private JButton blackJackButton;
    private JButton rouletteButton;

    public GameSelectionView() {
        this.render();
    }

    private void render() {

            this.setLayout(new GridLayout(1, 3));
            horseRaceButton = new JButton("Horse Races", new ImageIcon(ResourcesPath.HORSE_RACE_HOME_BUTTON_IMAGE));
            horseRaceButton.setHorizontalTextPosition(SwingConstants.CENTER);
            horseRaceButton.setForeground(Color.white);
            horseRaceButton.setFont(new Font("Serif", Font.BOLD, 20));


            blackJackButton = new JButton("BlackJack", new ImageIcon(ResourcesPath.BLACKJACK_HOME_BUTTON_IMAGE));
            blackJackButton.setHorizontalTextPosition(SwingConstants.CENTER);
            blackJackButton.setForeground(Color.white);
            blackJackButton.setFont(new Font("Serif", Font.BOLD, 20));

            rouletteButton = new JButton("Roulette",  new ImageIcon(ResourcesPath.ROULETTE_HOME_BUTTON_IMAGE));
            rouletteButton.setHorizontalTextPosition(SwingConstants.CENTER);
            rouletteButton.setForeground(Color.black);
            rouletteButton.setFont(new Font("Serif", Font.BOLD, 20));


            this.add(horseRaceButton);
            this.add(blackJackButton);
            this.add(rouletteButton);

    }

    @Override
    public void addActionController(ActionController actionController) {
        this.blackJackButton.addActionListener(actionController);
        this.blackJackButton.setActionCommand(ClientActionCommands.BLACKJACK_BUTTON_COMMAND);
        this.horseRaceButton.addActionListener(actionController);
        this.horseRaceButton.setActionCommand(ClientActionCommands.HORSE_RACE_BUTTON_COMMAND);
        this.rouletteButton.addActionListener(actionController);
        this.rouletteButton.setActionCommand(ClientActionCommands.ROULETTE_BUTTON_COMMAND);
    }
}