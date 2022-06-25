package view;

import constants.ViewIndexs;
import controller.ActionController;
import view.components.number_input_dialog.NumberInputDialog;
import view.views.HomeView;
import view.views.LoginView;

import javax.swing.*;
import java.awt.*;

public class ClientWindow extends Window {
    private ActionController actionController;
    private JPanel view;
    private CardLayout cardLayout;
    private LoginView loginView;
    private HomeView homeView;
    private NumberInputDialog numberInputDialog;
    private final String[] views = {
            "Login", "Home"
    };

    public ClientWindow() {
        super();


        this.buildViews();
        this.render();
    }

    private void buildViews() {
        this.loginView = new LoginView();
        this.homeView = new HomeView();
    }

    private void render() {
        // Create a panel with card layout to hold the view
        this.view = new JPanel();
        this.cardLayout = new CardLayout();
        this.view.setLayout(this.cardLayout);

        // Add views to card layout
        this.view.add(this.loginView, this.views[ViewIndexs.LOGIN_VIEW_INDEX]);
        this.view.add(this.homeView, this.views[ViewIndexs.HOME_VIEW_INDEX]);

        // Add view to the window
        this.setLayout(new BorderLayout());
        this.add(view, BorderLayout.CENTER);
    }

    @Override
    protected void configureWindow() {
        super.configureWindow();
        this.setTitle("Casino");
    }

    @Override
    public void addActionController(ActionController actionController) {
        this.actionController = actionController;
        this.loginView.addActionController(actionController);
        this.homeView.addActionController(actionController);
    }

    public void displayView(int viewIndex) {
        this.cardLayout.show(this.view, this.views[viewIndex]);
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public HomeView getHomeView() {
        return homeView;
    }



    public int createInputDialog() {
        String addedFunds = JOptionPane.showInputDialog("Enter the amount of funds to add");
        return Integer.parseInt(addedFunds);
    }
}
