package view.views.components;

import controller.ActionController;
import view.View;

import javax.swing.*;
import java.awt.*;

public class RankingUserComponent extends View {
    private final JLabel username;
    private final JLabel rankingPosition;
    private final JLabel amount;

    public RankingUserComponent(String username, int rankingPosition, int amount) {
        this.rankingPosition = new JLabel(String.valueOf(rankingPosition + 1));
        this.username = new JLabel(username);
        this.amount = new JLabel(String.valueOf(amount));

        this.render();
    }

    private void render() {
        // Configure layout
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createRaisedBevelBorder());

        // Add username label
        this.add(this.username, BorderLayout.CENTER, SwingConstants.CENTER);

        // Add ranking position label
        this.add(this.rankingPosition, BorderLayout.WEST);
        this.rankingPosition.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 50));
        // create a fixed size for the ranking position label
        this.rankingPosition.setPreferredSize(new Dimension(90, 90));

        // Add balance label
        this.add(this.amount, BorderLayout.EAST);
        this.amount.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));


        // Changing color of the ranking position label
        if(this.rankingPosition.getText().equals("1")) {
            this.rankingPosition.setForeground(new Color(186, 197, 12));
        } else if (this.rankingPosition.getText().equals("2")) {
            this.rankingPosition.setForeground(Color.LIGHT_GRAY);
        } else if (this.rankingPosition.getText().equals("3")) {
            this.rankingPosition.setForeground(new Color(148, 93, 65));
        }

        // Making the first ranking position label bold and bigger
        if(this.rankingPosition.getText().equals("1")) {
            this.rankingPosition.setFont(new Font(this.rankingPosition.getFont().getName(),
                    Font.BOLD, this.rankingPosition.getFont().getSize() + 15));
        } else if (this.rankingPosition.getText().equals("2")) {
            this.rankingPosition.setFont(new Font(this.rankingPosition.getFont().getName(),
                    Font.BOLD, this.rankingPosition.getFont().getSize() + 10));
        } else if (this.rankingPosition.getText().equals("3")) {
            this.rankingPosition.setFont(new Font(this.rankingPosition.getFont().getName(),
                    Font.BOLD, this.rankingPosition.getFont().getSize() + 5));
        }

        this.setPreferredSize(new Dimension(40, 50));
    }

    @Override
    public void addActionController(ActionController actionController) {
    }
}
