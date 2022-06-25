package view.views;

import constants.ResourcesPath;
import model.RouletteCell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Roulette extends JPanel implements Runnable {
    private BufferedImage rouletteIcon;
    private BufferedImage ballIcon;
    private int ballX;
    private int ballY;
    private double angle;
    private final double radius = 85;
    private final int valuesList[] = {0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26};
    private ArrayList<RouletteCell> rouletteCells;
    private boolean continueSpinning = true;
    private final double startAngle = 400.42;
    private final double nextCellAngleIncrement = 0.168;
    private double winningAngle;
    private int winningNumber;
    private int sleepTimer = 25;
    private boolean correctAngle;


    //ball on 0 is x:154 and y:69
    //ball on center is x:154 and y:152


    public Roulette() {
        ballX = 154;
        ballY = 69;
        rouletteCells = new ArrayList<>();
        assingAngleToNumber();
        // Load the images to the buffered images
        try {
            rouletteIcon = ImageIO.read(new File("./images/roulette.png"));
            ballIcon = ImageIO.read(new File("./images/ball.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        // set the angle to the 0 cell
        this.angle = startAngle;

        // Set the background color
        this.setBackground(ResourcesPath.backgroundColor);
    }

    public ArrayList<RouletteCell> getRouletteCells() {
        return rouletteCells;
    }

    /**
     * This method spins the ball
     */
    public void spinBall() {
        //Go to the next cell
        if (this.angle >= 406.636) this.angle = startAngle;



        angle += this.nextCellAngleIncrement;

        //Move ball X and y to de desired cell
        ballX = (int) (Math.cos(angle) * radius);
        ballY = (int) (Math.sin(angle) * radius);

        // Set center of the circumference
        ballX += 152;
        ballY += 154;
        //repaint the roulette with the ball on the desired position
        this.repaint();
    }


    /**
     * This method assigns an angle to each roulette number
     */
    private void assingAngleToNumber() {
        double angleValue = startAngle;
        for (int i : valuesList) {
            if (i == 0) {
                rouletteCells.add(new RouletteCell(i, angleValue, RouletteCell.GREEN));
            }
            if ((i >= 1 && i <= 10) || i >= 19 && i <= 28) {

                if (i % 2 == 0) rouletteCells.add(new RouletteCell(i, angleValue, RouletteCell.BLACK));
                else rouletteCells.add(new RouletteCell(i, angleValue, RouletteCell.RED));

            } else if (i >= 11 && i <= 18 || i >= 29 && i <= 36) {
                if (i % 2 == 0) rouletteCells.add(new RouletteCell(i, angleValue, RouletteCell.RED));
                else rouletteCells.add(new RouletteCell(i, angleValue, RouletteCell.BLACK));
            }
            angleValue += nextCellAngleIncrement;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(rouletteIcon, 0, 0, 320, 320, null);
        g.drawImage(ballIcon, ballX, ballY, 12, 12, null);
    }

    /**
     * Changes the condition for the ball to stop spinning
     */
    void stopSpinning() {
        this.continueSpinning = false;
    }


    /**
     * Randomizes the number that wins the round
     *
     * @return number that wins the round
     */
    public int getLuckyNumber() {
        return this.winningNumber;
    }

    /**
     * This method converts the number to the angle
     *
     * @param winningNumber
     */
    private void convertNumberToAngle(int winningNumber) {
        for (RouletteCell r : rouletteCells) {
            if (r.getValue() == winningNumber) {
                winningAngle = r.getAngle();
                break;
            }
        }
    }


    /**
     * Method that keeps the ball running independently with a slight delay until the spinning
     * condition becomes false
     */
    @Override
    public void run() {
        correctAngle = false;
        try {
            while (true) {
                while (continueSpinning || !correctAngle) {
                    if (!continueSpinning && Math.abs(this.angle - winningAngle) < 0.1) {
                        correctAngle = true;

                    }
                    if (!continueSpinning) sleepTimer += 10;
                    spinBall();


                    Thread.sleep(sleepTimer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the condition for the ball to continue spinning
     */
    public void continueSpinning() {
        this.sleepTimer = 25;
        this.continueSpinning = true;
        this.correctAngle = false;
    }

    public void setWinningNumber(int number) {
        this.winningNumber = number;
        continueSpinning = false;
        convertNumberToAngle(winningNumber);
    }
}
