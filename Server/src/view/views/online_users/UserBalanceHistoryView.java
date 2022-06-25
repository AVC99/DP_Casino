package view.views.online_users;

import controller.ActionController;
import model.users.User;
import persistence.mysql.DatabaseManager;
import view.View;
import view.views.components.BalanceGraphic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserBalanceHistoryView extends View {
    private User user;
    private BalanceGraphic balanceGraphic;
    private DatabaseManager databaseManager;
    private ArrayList<Integer> balanceHistory;
    private JPanel centerPanel;


    public UserBalanceHistoryView() {
        createBalanceArray();
        render();
    }

    private void render() {
        this.setLayout(new BorderLayout());
    }

    private void createBalanceArray() {
        this.add(new JLabel("Balance History"));
        this.balanceHistory= new ArrayList<>();
    }

    private void createGraphic() {
        this.centerPanel= new JPanel();
        this.centerPanel.setLayout(new BorderLayout());
        this.balanceHistory.addAll(databaseManager.getHistoryBalance(this.user.getUsername()));


        this.balanceGraphic= new BalanceGraphic(this.balanceHistory);
        this.centerPanel.add(this.balanceGraphic, BorderLayout.CENTER);
        this.add(this.centerPanel, BorderLayout.CENTER);
    }

    public void setUser(User user) {
        //this.removeAll();
        this.user = user;
        System.out.println("user added to history view");
        //this.add(new JLabel(this.getClass().getSimpleName() + ": test " + this.user.getUsername()));
    }
    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        createGraphic();
    }

    @Override
    public void addActionController(ActionController actionController) {

    }
}
