package view;

import controller.ActionController;

import javax.swing.*;
import java.awt.*;

public abstract class Window extends JFrame {
    private static final int WINDOW_WIDTH = 1080;
    private static final int WINDOW_HEIGHT = 720;


    public Window() {
        this.configureWindow();
    }

    protected void configureWindow() {
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public abstract void addActionController(ActionController actionController);

    public void showDialogNotification(String title, String message, boolean isError) {
        final JDialog dialog = new JDialog(this, title, false);
        JOptionPane optionPane = new JOptionPane(message, isError ?
                JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE );

        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        optionPane.addPropertyChangeListener(e -> {
            String prop = e.getPropertyName();

            if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                dialog.setVisible(false);
            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(null);

        dialog.setVisible(true);
        dialog.requestFocus();
    }


}
