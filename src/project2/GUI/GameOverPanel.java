package project2.GUI;

import project2.Pets.Pet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel {

    private final JLabel messageLabel;
    private final JLabel reasonLabel;

    private final JButton menuButton;
    private final JButton exitButton;

    public GameOverPanel() {
        setLayout(null);

        JLabel gameOverLabel = new JLabel("Game Over");
        messageLabel = new JLabel("Loading Message...");
        reasonLabel = new JLabel("Loading Reason...");

        menuButton = new JButton("Menu");
        exitButton = new JButton("Exit");

        Rectangle r = new Rectangle(0, 0, 600, 400);


        gameOverLabel.setBounds(210, 100, 200, 50);
        messageLabel.setBounds(r.x+100, r.y+50, r.width-200, r.height-200);
        reasonLabel.setBounds(r.x+100, r.y+70, r.width-200, r.height-200);

        menuButton.setBounds(150, 200, 80, 20);
        exitButton.setBounds(250, 200, 80, 20);

        add(gameOverLabel);
        add(messageLabel);
        add(reasonLabel);
        add(menuButton);
        add(exitButton);

    }

    public void addActionListeners(ActionListener listener) {
        menuButton.addActionListener(listener);
        exitButton.addActionListener(listener);
    }

    public void setMessage(Pet pet, int cycles) {
        String message = "";
        message += "You lasted " + cycles + " cycles and your pet lived to be " + pet.getAge() + " years old!";
        if (pet.getHappiness() < 1) {
            reasonLabel.setText("Your pet left you because it was unhappy.");
        } else {
            reasonLabel.setText("Your pet died.");
        }
        messageLabel.setText(message);
    }
}
