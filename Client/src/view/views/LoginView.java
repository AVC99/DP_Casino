package view.views;

import constants.ClientActionCommands;
import controller.ActionController;
import utils.Fonts;
import view.View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends View {
    private JPanel form;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JButton signInButton;
    private JButton signUpButton;
    private JButton playAsGuestButton;

    public LoginView() {
        this.render();
    }

    private void buildForm() {
        // Create a panel
        this.form = new JPanel();
        this.form.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Create a title
        JLabel title = new JLabel("Welcome to Casino!");
        title.setFont(Fonts.TITLE);

        // Create a panel for the username
        JPanel username = new JPanel();
        JLabel usernameLabel = new JLabel("Username");
        this.usernameInput = new JTextField(20);
        username.add(usernameLabel);
        username.add(usernameInput);

        // Create a panel for the password
        JPanel password = new JPanel();
        JLabel passwordLabel = new JLabel("Password");
        this.passwordInput = new JPasswordField(20);
        password.add(passwordLabel);
        password.add(passwordInput);

        // Create a panel for the buttons
        JPanel buttons = new JPanel();
        this.signInButton = new JButton("SIGN IN");
        this.signUpButton = new JButton("SIGN UP");
        this.playAsGuestButton = new JButton("PLAY AS GUEST");
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 30));
        buttons.add(this.signInButton);
        buttons.add(this.signUpButton);
        buttons.add(this.playAsGuestButton);

        // Add components to form properly
        c.insets = new Insets(0, 0, 40, 0);
        c.gridx = 0;
        c.gridy = 0;
        this.form.add(title, c);
        c.insets = new Insets(0,0, 20, 0);
        c.gridy = 1;
        this.form.add(username, c);
        c.gridy = 2;
        this.form.add(password, c);
        c.gridy = 3;
        this.form.add(buttons, c);
    }

    private void render() {
        this.buildForm();

        this.setLayout(new BorderLayout());
        this.add(this.form, BorderLayout.CENTER);
    }

    public String getUsername() {
        return this.usernameInput.getText();
    }

    public String getPassword() {
        return String.valueOf(this.passwordInput.getPassword());
    }

    @Override
    public void addActionController(ActionController actionController) {
        this.signInButton.setActionCommand(ClientActionCommands.SIGN_IN_BUTTON_COMMAND);
        this.signInButton.addActionListener(actionController);

        this.signUpButton.setActionCommand(ClientActionCommands.SIGN_UP_BUTTON_COMMAND);
        this.signUpButton.addActionListener(actionController);

        this.playAsGuestButton.setActionCommand(ClientActionCommands.PLAY_AS_GUEST_BUTTON_COMMAND);
        this.playAsGuestButton.addActionListener(actionController);
    }
}
