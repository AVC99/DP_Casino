package view.components.number_input_dialog;

import constants.ClientActionCommands;
import controller.ActionController;
import model.BetType;
import view.Window;

import javax.swing.*;
import java.awt.*;

public class NumberInputDialog extends Window {
    private NumberInputDialogContext context;
    private String windowTitle;
    private String message;
    private JTextField input;
    private JButton acceptButton;
    private BetType betType;

    public NumberInputDialog(NumberInputDialogContext context, String windowTitle, String message) {
        this.context = context;
        this.windowTitle = windowTitle;
        this.message = message;
        this.betType=null;

        this.configureWindow();
        this.render();
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public BetType getBetType() {
        return betType;
    }

    private void render() {
        // Create components
        JPanel content = new JPanel();
        JLabel message = new JLabel(this.message);
        this.input = new JTextField(20);
        this.acceptButton = new JButton("Accept");

        // Add components to view
        content.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(0, 0, 20, 0);
        gb.gridx = 0;
        gb.gridy = 0;
        content.add(message, gb);
        gb.gridy = 1;
        content.add(this.input, gb);
        gb.gridy = 2;
        content.add(this.acceptButton, gb);
        this.add(content);
    }

    @Override
    protected void configureWindow() {
        this.setSize(new Dimension(400, 200));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(Window.DISPOSE_ON_CLOSE);
        this.setTitle(this.windowTitle);
    }

    public NumberInputDialogContext getContext() {
        return context;
    }

    public String getInputNumber() {
        return this.input.getText();
    }


    @Override
    public void addActionController(ActionController actionController) {
        this.acceptButton.setActionCommand(ClientActionCommands.NUMBER_INPUT_DIALOG_ACCEPT_BUTTON_COMMAND);
        this.acceptButton.addActionListener(actionController);
    }
}
