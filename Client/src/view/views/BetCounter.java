package view.views;

import controller.ActionController;
import view.View;

import java.awt.*;

public class BetCounter extends View {
    private int currentBet;

    public BetCounter(int currentBet){
        this.currentBet=currentBet;
        render();
    }

    private void render() {
        this.setBackground(Color.RED);
    }

    @Override
    public void addActionController(ActionController actionController) {

    }
}
