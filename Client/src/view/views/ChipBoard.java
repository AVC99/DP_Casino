package view.views;

import constants.ClientActionCommands;
import controller.ActionController;
import view.View;


import javax.swing.*;
import java.awt.*;


public class ChipBoard extends View {
    private JButton fiveChip;
    private JButton tenChip;
    private JButton fiftyChip;
    private JButton oneHundredChip;
    private JButton fiveHundredChip;
    private  ImageIcon buttonIcon;

    public ChipBoard(Color bg){
        render(bg);
        configureButtons();
    }

    private void configureButtons() {
        try {
            //buttonIcon = ImageIO.read(new File("./images/chip5euros.png"));
            buttonIcon= new ImageIcon("./images/chip5euros.png");
            resizeImage();
            fiveChip = new JButton(buttonIcon);
            fiveChip.setOpaque(false);
            fiveChip.setContentAreaFilled(false);
            fiveChip.setBorderPainted(false);
            this.add(fiveChip);


            buttonIcon= new ImageIcon("./images/chip10euros.png");
            resizeImage();
            tenChip = new JButton(buttonIcon);
            tenChip.setOpaque(false);
            tenChip.setContentAreaFilled(false);
            tenChip.setBorderPainted(false);
            this.add(tenChip);

            buttonIcon= new ImageIcon("./images/chip50euros.png");
            resizeImage();
            fiftyChip = new JButton(buttonIcon);
            fiftyChip.setOpaque(false);
            fiftyChip.setContentAreaFilled(false);
            fiftyChip.setBorderPainted(false);
            this.add(fiftyChip);



            buttonIcon= new ImageIcon("./images/chip100euros.png");
            resizeImage();
            oneHundredChip= new JButton(buttonIcon);
            oneHundredChip.setOpaque(false);
            oneHundredChip.setContentAreaFilled(false);
            oneHundredChip.setBorderPainted(false);
            this.add(oneHundredChip);

            buttonIcon= new ImageIcon("./images/chip500euros.png");
            resizeImage();
            fiveHundredChip= new JButton(buttonIcon);
            fiveHundredChip.setOpaque(false);
            fiveHundredChip.setContentAreaFilled(false);
            fiveHundredChip.setBorderPainted(false);
            this.add(fiveHundredChip);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void resizeImage() {
        Image newimg;
        Image image;
        image = buttonIcon.getImage(); // transform it
        newimg = image.getScaledInstance(75, 75,  Image.SCALE_SMOOTH);
        buttonIcon = new ImageIcon(newimg);

    }

    private void render(Color bg) {
        this.setLayout(new GridLayout(1,5));
        this.setBackground(bg);
    }

    @Override
    public void addActionController(ActionController actionController) {
       fiveChip.addActionListener(actionController);
       fiveChip.setActionCommand(ClientActionCommands.FIVE_CHIP_BUTTON_COMMAND);
       tenChip.addActionListener(actionController);
       tenChip.setActionCommand(ClientActionCommands.TEN_CHIP_BUTTON_COMMAND);
       fiftyChip.addActionListener(actionController);
       fiftyChip.setActionCommand(ClientActionCommands.FIFTY_CHIP_BUTTON_COMMAND);
       oneHundredChip.addActionListener(actionController);
       oneHundredChip.setActionCommand(ClientActionCommands.HUNDRED_CHIP_BUTTON_COMMAND);
       fiveHundredChip.addActionListener(actionController);
       fiveHundredChip.setActionCommand(ClientActionCommands.FIVE_HUNDRED_CHIP_BUTTON_COMMAND);
    }
}
