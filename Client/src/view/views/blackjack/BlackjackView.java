package view.views.blackjack;

import constants.ClientActionCommands;
import constants.ResourcesPath;
import model.Bet;
import model.BetType;
import model.BlackJackResults;
import model.blackjack.BlackjackCard;
import model.blackjack.BlackjackDeck;
import utils.Colors;
import controller.ActionController;
import view.View;
import view.views.HomeView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BlackjackView extends View {
    private BufferedImage backCardImage;
    private BlackjackDeck deck;
    private ArrayList<BlackjackCard> dealerHand;
    private ArrayList<BlackjackCard> playerHand;
    private JButton startButton;
    private JButton hitButton;
    private JButton stayButton;
    private JButton doubleButton;
    private boolean dealerHasStayed;
    private boolean playerHasStayed;
    private boolean inGame;

    public BlackjackView() {
        this.dealerHand = new ArrayList<>();
        this.playerHand = new ArrayList<>();
        this.inGame = false;

        this.render();
    }

    private void render() {
        // Set layout
        this.setLayout(new BorderLayout());

        // Create components
        JPanel buttons = new JPanel(new GridLayout(4, 1));
        this.startButton = new JButton("Start");
        this.hitButton = new JButton("Hit");
        this.hitButton.setEnabled(false);
        this.doubleButton = new JButton("Double");
        this.doubleButton.setEnabled(false);
        this.stayButton = new JButton("Stay");
        this.stayButton.setEnabled(false);


        // Add components to view
        buttons.add(startButton);
        buttons.add(hitButton);
        buttons.add(doubleButton);
        buttons.add(stayButton);
        this.add(buttons, BorderLayout.EAST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Paint background
        this.setBackground(Colors.DARK_GREEN);

        // Get back card image
        this.backCardImage = this.getAsset(ResourcesPath.BLACKJACK_BACK_CARD_IMAGE_PATH);

        // Draw the card stack (decoration)
        g2d.drawImage(this.backCardImage, 0, ((this.getHeight() - HomeView.HEIGHT_USER_PANEL) / 2) -
                        (this.backCardImage.getHeight() / 6), this.backCardImage.getWidth() / 4,
                this.backCardImage.getHeight() / 4, null);

        // Draw dealer's hand of cards
        this.renderHand(g2d, this.dealerHand, false);

        // Draw player's hand of cards
        this.renderHand(g2d, this.playerHand, true);
    }

    private BufferedImage getAsset(String assetPath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(assetPath));
        } catch (IOException e) {
            System.out.println("ERROR: Cannot find the asset " + assetPath + "!");
        }

        return image;
    }

    private void renderHand(Graphics2D g2d, ArrayList<BlackjackCard> hand, boolean isPlayer) {
        int positionY;
        int spacing = 0;

        for(BlackjackCard card : hand) {
            BufferedImage image = this.getAsset(ResourcesPath.BLACKJACK_CARD_IMAGE_PATH + card.getValue() +
                    card.getSuit() + ResourcesPath.BLACKJACK_CARD_IMAGE_EXTENSION_PATH);

            // Check if card must be rendered on top (dealer) or bottom (player)
            if(isPlayer) {
                positionY = this.getHeight() - (image.getHeight() / 4) - 20;
            } else {
                positionY = HomeView.HEIGHT_USER_PANEL / 2;
            }

            // Check if is the dealer's hand and must be face down
            if(isPlayer) {
                g2d.drawImage(image, (this.getWidth() / 2) - (image.getWidth() / 2) + spacing, positionY,
                        image.getWidth() / 4,image.getHeight() / 4, null);
            } else if(hand.get(0).equals(card)) {
                g2d.drawImage(image, (this.getWidth() / 2) - (image.getWidth() / 2) + spacing, positionY,
                        image.getWidth() / 4,image.getHeight() / 4, null);
            } else {
                g2d.drawImage(this.backCardImage, (this.getWidth() / 2) - (image.getWidth() / 2) + spacing,
                        positionY,image.getWidth() / 4,image.getHeight() / 4, null);
            }

            spacing += 50;
        }
    }

    @Override
    public void addActionController(ActionController actionController) {
        this.startButton.setActionCommand(ClientActionCommands.BLACKJACK_START_BUTTON_COMMAND);
        this.startButton.addActionListener(actionController);

        this.hitButton.setActionCommand(ClientActionCommands.BLACKJACK_HIT_BUTTON_COMMAND);
        this.hitButton.addActionListener(actionController);

        this.doubleButton.setActionCommand(ClientActionCommands.BLACKJACK_DOUBLE_BUTTON_COMMAND);
        this.doubleButton.addActionListener(actionController);

        this.stayButton.setActionCommand(ClientActionCommands.BLACKJACK_STAY_BUTTON_COMMAND);
        this.stayButton.addActionListener(actionController);
    }

    public void startGame() {
        // Set in game
        this.inGame = true;
        this.toggleButtons(true);

        // Set dealer and player hands to empty
        this.dealerHand.clear();
        this.playerHand.clear();
        this.dealerHasStayed = false;
        this.playerHasStayed = false;

        // Remove possible existing cards from older games
        this.repaint();

        // TODO Ask for a bet

        // Get a new deck and shuffle it
        this.deck = new BlackjackDeck();
        this.deck.shuffleDeck();

        // Give to dealer and player one card to each
        this.dealerHand.add(this.deck.removeCard(this.deck.getDeck().size() - 1));
        this.playerHand.add(this.deck.removeCard(this.deck.getDeck().size() - 1));

        // Update UI
        SwingUtilities.invokeLater(this::repaint);
    }

    public BlackJackResults hit() {
        // Give a card to the dealer if he/she has not stayed
        if(!this.dealerHasStayed) {
            this.dealerHand.add(this.deck.removeCard(this.deck.getDeck().size() - 1));
        }

        // Give a card to the player if he/she has not stayed
        if(!this.playerHasStayed) {
            this.playerHand.add(this.deck.removeCard(this.deck.getDeck().size() - 1));
        }

        // Update UI
        SwingUtilities.invokeLater(this::repaint);

        return this.checkScores();
    }

    public BlackJackResults stay() {
        // Mark player as stayed
        this.playerHasStayed = true;
        this.toggleButtons(false);
        BlackJackResults result= BlackJackResults.DRAW;

        // Check if dealer is stayed
        if(!this.dealerHasStayed) {
            // Give cards to dealer until he/she stays
            while (!this.dealerHasStayed) {
               result= this.hit();
            }
        } else {
           return this.checkScores();
        }
        return result;
    }

    private BlackJackResults checkScores() {
        // Get dealer score
        int dealerCount = 0;
        for(BlackjackCard card : this.dealerHand) {
            dealerCount += card.getValue();
        }

        // Get player score
        int playerCount = 0;
        for(BlackjackCard card : this.playerHand) {
            playerCount += card.getValue();
        }

        // Check if dealer has passed 17 to stay
        if(dealerCount >= 17) {
            this.dealerHasStayed = true;
        }

        // Check if player has passed 21
        if(playerCount >= 21) {
            // Mark player as loser
            this.playerHasStayed = true;
            this.toggleButtons(false);
        }

        // Check for the winner
        if(this.playerHasStayed && this.dealerHasStayed) {
            // Check scores
            if (dealerCount > 21 && playerCount > 21) {
                System.out.println("No winner");
                showCounts(dealerCount, playerCount);
                return BlackJackResults.DRAW;

            } else if(dealerCount > 21) {
                if(playerCount <= 21) {

                    System.out.println("Player wins");
                    showCounts(dealerCount, playerCount);
                    return BlackJackResults.PLAYER_WIN;
                }
            } else if(playerCount > 21) {
                if(dealerCount <= 21) {
                    System.out.println("Dealer wins");
                    showCounts(dealerCount, playerCount);
                    return BlackJackResults.PLAYER_LOSE;
                }
            } else if (dealerCount > playerCount && dealerCount <= 21) {
                System.out.println("Dealer wins");
                return BlackJackResults.PLAYER_LOSE;
            } else if (playerCount > dealerCount && playerCount <= 21) {
                System.out.println("Player wins");
                showCounts(dealerCount, playerCount);
                return BlackJackResults.PLAYER_WIN;
            } else {
                System.out.println("No winner");
                showCounts(dealerCount, playerCount);
                return BlackJackResults.DRAW;
            }
        }
       return BlackJackResults.CONTINUE;
    }

    public void showResultPane(BlackJackResults result, int amount) {
        switch (result) {
            case PLAYER_WIN->JOptionPane.showMessageDialog(null, "You won " + amount );
            case PLAYER_LOSE-> JOptionPane.showMessageDialog(null, "You lost " + amount );
            case DRAW-> JOptionPane.showMessageDialog(null, "Draw");
        }
    }

    private void showCounts(int dealerCount, int playerCount) {
        System.out.println("Player: " + playerCount);
        System.out.println("Dealer: " + dealerCount + "\n");
    }

    public void doubleBet() {
      this.doubleButton.setEnabled(false);
      this.hit();
      this.stay();
    }

    private void toggleButtons(boolean status) {
        this.hitButton.setEnabled(status);
        this.stayButton.setEnabled(status);
        this.doubleButton.setEnabled(status);
    }



    public Bet addBet(BetType betType){
        String amountString=JOptionPane.showInputDialog("Enter Bet Amount");
        int amount= Integer.parseInt(amountString); ;
        return new Bet(amount,betType);
    }
}
