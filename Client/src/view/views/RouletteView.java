package view.views;

import constants.ResourcesPath;
import controller.ActionController;
import model.Bet;
import model.BetType;
import view.View;

import javax.swing.*;
import java.awt.*;


public class RouletteView extends View{
    private GridBagLayout boardLayout;
    private JPanel centerPanel;
    private JPanel westPanel;
    private JPanel betPanel;
    private Roulette roulette;
    private Color bgColor;
    private RouletteBoard rouletteBoard;
    private JPanel southPanel;
    private ChipBoard chipBoard;
    private boolean firstTime;




    public RouletteView(){
        this.bgColor= ResourcesPath.backgroundColor;
        this.render();
        this.firstTime=true;
        this.configureWest();
        this.configureCenter();
        this.startThread();
    }

    public void startThread(){
        new Thread(roulette).start();
        System.out.println("Thread started");
    }

    private void configureCenter() {
        this.centerPanel.setLayout(new GridLayout(1,1));
        rouletteBoard = new RouletteBoard();
        this.centerPanel.add(rouletteBoard);


    }

    private void configureWest() {
        this.configureRoulette();
    }

    public Bet addBet(BetType betType){
        String amountString=JOptionPane.showInputDialog("Enter Bet Amount");
        int amount= Integer.parseInt(amountString); ;
        return new Bet(amount,betType);
    }


    private void configureRoulette() {
        roulette= new Roulette();
        roulette.setPreferredSize(new Dimension(320,320));
        westPanel.add(roulette, BorderLayout.CENTER);
        betPanel= new JPanel();

    }

    public void spin(){
        System.out.println("roulette told to spin");
        roulette.continueSpinning();
        if(!firstTime){
            //roulette.run();
        }
    }

    public void stopSpinning(){
        System.out.println("roulete told to stop spinning");
       int luckyNumber= roulette.getLuckyNumber();
         roulette.stopSpinning();
         firstTime=false;
        System.out.println("Winning number: " + luckyNumber);
    }

    private void render() {
        this.setLayout(new BorderLayout());
        this.boardLayout= new GridBagLayout();

        centerPanel = new JPanel(boardLayout);
        westPanel = new JPanel();
        this.southPanel=new JPanel();


        this.westPanel.setBackground(bgColor);
        this.westPanel.setLayout(new BorderLayout());
        this.add(westPanel,BorderLayout.WEST);
        this.westPanel.setLayout(new BorderLayout());

        this.centerPanel.setBackground(bgColor);
        this.add(centerPanel, BorderLayout.CENTER);

        this.southPanel.setBackground(bgColor);
        this.southPanel.setLayout(new GridLayout(1,1));
        this.add(southPanel, BorderLayout.SOUTH);

    }

    @Override
    public void addActionController(ActionController actionController) {

    this.rouletteBoard.addActionController(actionController);

    }

    public void setWinningNumber(int parseInt) {
        this.roulette.setWinningNumber(parseInt);
    }

    public RouletteBoard getRouletteBoard() {
        return rouletteBoard;
    }

    public Roulette getRoulette() {
        return roulette;

    }

   /*public NumberInputDialog getBetInputDialog() {
        return betInputDialog;
    }*/
}
