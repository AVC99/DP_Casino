package view.views;

import constants.ClientActionCommands;
import constants.ResourcesPath;
import controller.ActionController;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RouletteBoard extends View {
    private ArrayList<JButton> buttonList;
    private JButton numberButton;


    public RouletteBoard(){
        this.buttonList= new ArrayList<>();
        this.render();
        this.configureButtons();

    }

    private void configureButtons() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill= GridBagConstraints.VERTICAL;
        

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridheight=3;
        this.numberButton= new JButton("0");
        this.numberButton.setBackground(Color.green);

        this.buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);


        gridBagConstraints.gridheight=1;
        gridBagConstraints.gridwidth=1;
        int buttonNumber=0;
        String text;

        for(int i=1; i<13;i++){
            gridBagConstraints.gridx=i;
            for(int j=0;j<3;j++){
                buttonNumber++;
                text= String.valueOf(buttonNumber);
                gridBagConstraints.gridy=j;
                numberButton= new JButton(text);
                if((buttonNumber>=1 && buttonNumber<=10) || buttonNumber>=19 && buttonNumber<=28 ){
                    if (buttonNumber%2==0)paintButtonBlack();
                    else paintButtonRed();

                }else if (buttonNumber>=11&& buttonNumber<=18|| buttonNumber>=29&& buttonNumber<=36){
                    if (buttonNumber%2==0)paintButtonRed();
                    else paintButtonBlack();
                }
                this.buttonList.add(numberButton);
                this.add(numberButton, gridBagConstraints);
            }
        }

        // configure the 12's bets
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=4;
        gridBagConstraints.gridwidth=4;
        gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
        numberButton=new JButton("First 12");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        gridBagConstraints.gridx=5;
        gridBagConstraints.gridy=4;
        numberButton=new JButton("Second 12");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        gridBagConstraints.gridx=9;
        gridBagConstraints.gridy=4;
        numberButton=new JButton("Third 12");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        // configure the row bets
        gridBagConstraints.gridx=14;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("First row");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        gridBagConstraints.gridx=14;
        gridBagConstraints.gridy=1;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("Second row");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        gridBagConstraints.gridx=14;
        gridBagConstraints.gridy=2;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("Third row");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        //1-18 Bet and 19-36 Bet
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=5;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("1-18");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        gridBagConstraints.gridx=11;
        gridBagConstraints.gridy=5;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("19-36");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        //even and odd bets
        gridBagConstraints.gridx=3;
        gridBagConstraints.gridy=5;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("Even");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        gridBagConstraints.gridx=9;
        gridBagConstraints.gridy=5;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("Odd");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        paintButtonGreenGolden();

        // Black or red bet
        gridBagConstraints.gridx=5;
        gridBagConstraints.gridy=5;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("Red");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        this.paintButtonRed();

        gridBagConstraints.gridx=7;
        gridBagConstraints.gridy=5;
        gridBagConstraints.gridwidth=2;
        numberButton=new JButton("Black");
        buttonList.add(numberButton);
        this.add(numberButton, gridBagConstraints);
        this.paintButtonBlack();



    }

    private void paintButtonBlack() {
        numberButton.setBackground(Color.black);
        this.numberButton.setForeground(Color.white);
    }

    private void paintButtonRed() {
        numberButton.setBackground(Color.red);
        this.numberButton.setForeground(Color.black);
    }

    private void paintButtonGreenGolden(){
        numberButton.setBackground(new Color(15,111,48));
        this.numberButton.setForeground(new Color(254,218,91));
    }
    private void render() {
        this.setBackground(ResourcesPath.backgroundColor);
        GridBagLayout boardLayout = new GridBagLayout();
        this.setLayout(boardLayout);
    }

    @Override
    public void addActionController(ActionController actionController) {
        for(int i=0; i<buttonList.size() ;i++){
            buttonList.get(i).addActionListener(actionController);
            buttonList.get(i).setActionCommand(ClientActionCommands.ROULETTE_BUTTON_COMMAND_LIST[i]);
        }
    }

    public void toggleButtons(boolean enable){
        for(int i=0; i<buttonList.size() ;i++){
            buttonList.get(i).setEnabled(enable);
        }
    }

}
