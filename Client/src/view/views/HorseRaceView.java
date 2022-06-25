package view.views;

import constants.ClientActionCommands;
import controller.ActionController;
import model.Bet;
import model.BetType;
import model.Horse;
import view.View;

import javax.swing.*;
import java.awt.*;

public class HorseRaceView extends View {
    private HorseRaceField horseRaceField;
    private JPanel southPanel;
    private JButton betForPurpleButton;
    private JButton betForRedButton;
    private JButton betForBlueButton;
    private String winner;
    private boolean raceFinished;
    private int raceCount;

    public HorseRaceView(){
        raceFinished= false;
        this.render();
        this.winner=null;
        raceCount=0;

    }
    public void informResult(int amount, boolean isVictory) {
        if(isVictory){
            JOptionPane.showMessageDialog(null, "Congratulation you have won " + amount );
        }
        JOptionPane.showMessageDialog(null, "You have lost");
    }

    public HorseRaceField getHorseRaceField() {
        return horseRaceField;
    }

    public void startRace(){
       // if (raceCount==0){

            //raceCount++;
       // }
        this.getHorseRaceField().startRace();
        new Thread(horseRaceField).start();
        //this.getHorseRaceField().run();

    }
    private void render() {

        this.setLayout(new BorderLayout());

        this.horseRaceField= new HorseRaceField();
        horseRaceField.setPreferredSize(new Dimension(this.getHeight(),this.getWidth()));
        this.add(horseRaceField,BorderLayout.CENTER);

        this.southPanel= new JPanel();
        southPanel.setLayout(new GridLayout(1,3));
        this.betForBlueButton= new JButton("Bet for BLUE");
        this.betForRedButton= new JButton("Bet for RED");
        this.betForPurpleButton= new JButton("Bet for PURPLE");

        this.southPanel.add(betForBlueButton);
        this.southPanel.add(betForPurpleButton);
        this.southPanel.add(betForRedButton);

        this.add(southPanel,BorderLayout.SOUTH);
    }

    public Bet addBet(BetType betType){
        String amountString=JOptionPane.showInputDialog("Enter Bet Amount");
        int amount= Integer.parseInt(amountString);
        return new Bet(amount,betType);
    }

    public void setWinner(){
        if (raceFinished){
            for(Horse h: horseRaceField.getHorseList()){
                if(h.isWinningHorse()){
                    this.winner=h.getHorseColor();
                }
            }
        }
    }

    public void hasRaceFinished(){
        for(Horse h: horseRaceField.getHorseList()){
            if (h.isWinningHorse()) {
                this.raceFinished = true;
                break;
            }
        }
    }

    @Override
    public void addActionController(ActionController actionController) {
        this.betForBlueButton.addActionListener(actionController);
        this.betForBlueButton.setActionCommand(ClientActionCommands.HORSE_RACE_BET_BLUE_BUTTON_COMMAND);

        this.betForPurpleButton.addActionListener(actionController);
        this.betForPurpleButton.setActionCommand(ClientActionCommands.HORSE_RACE_BET_PURPLE_BUTTON_COMMAND);

        this.betForRedButton.addActionListener(actionController);
        this.betForRedButton.setActionCommand(ClientActionCommands.HORSE_RACE_BET_RED_BUTTON_COMMAND);
    }

    public void toggleBetButtons(boolean enable) {
        this.betForBlueButton.setEnabled(enable);
        this.betForRedButton.setEnabled(enable);
        this.betForPurpleButton.setEnabled(enable);
    }
}
