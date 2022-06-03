package project2.GUI;

import project2.Pets.Pet;
import project2.Pets.PetType;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {

    private final JLabel cycleLabel;
    private final JLabel yearLabel;

    private final JLabel happinessLabel;
    private final JLabel hungerLabel;
    private final JLabel healthLabel;
    private final JLabel nameLabel;
    private final JLabel ageLabel;
    private final JLabel typeLabel;

    private final JButton nextCycleButton;
    private final JButton playButton;
    private final JButton feedButton;
    private final JButton healButton;

    private final JTextField renameTextField;
    private final JButton renameButton;

    private final JButton menuButton;

    public GamePanel() {
        setLayout(null);

        cycleLabel = new JLabel("Cycle: ?");
        yearLabel = new JLabel("Year: ?");

        happinessLabel = new JLabel("Happiness: ?");
        hungerLabel = new JLabel("Hunger: ?");
        healthLabel = new JLabel("Health: ?");
        nameLabel = new JLabel("Name: ?");
        ageLabel = new JLabel("Age: ?");
        typeLabel = new JLabel("Type: ?");

        nextCycleButton = new JButton("Next Cycle");
        playButton = new JButton("Play");
        feedButton = new JButton("Feed");
        healButton = new JButton("Heal");

        JLabel renameLabel = new JLabel("Rename: ");
        renameTextField = new JTextField(10);
        renameButton = new JButton("Rename");

        menuButton = new JButton("Menu");

        JPanel cyclePanel = new JPanel();
        cyclePanel.setLayout(null);
        cycleLabel.setBounds(10, 10, 100, 20);
        yearLabel.setBounds(10, 30, 100, 20);

        cyclePanel.add(cycleLabel);
        cyclePanel.add(yearLabel);

        cyclePanel.setBounds(10, 10, 100, 50);
        add(cyclePanel);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(null);
        nameLabel.setBounds(10, 10, 100, 20);
        ageLabel.setBounds(10, 30, 100, 20);
        typeLabel.setBounds(10, 50, 100, 20);

        happinessLabel.setBounds(240, 10, 100, 20);
        hungerLabel.setBounds(240, 30, 100, 20);
        healthLabel.setBounds(240, 50, 100, 20);

        statsPanel.add(nameLabel);
        statsPanel.add(ageLabel);
        statsPanel.add(typeLabel);

        statsPanel.add(happinessLabel);
        statsPanel.add(hungerLabel);
        statsPanel.add(healthLabel);

        statsPanel.setBounds(240, 10, 400, 100);
        add(statsPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        nextCycleButton.setBounds(0, 10, 120, 20);
        playButton.setBounds(140, 10, 120, 20);
        feedButton.setBounds(280, 10, 120, 20);
        healButton.setBounds(420, 10, 120, 20);

        buttonPanel.add(nextCycleButton);
        buttonPanel.add(playButton);
        buttonPanel.add(feedButton);
        buttonPanel.add(healButton);

        buttonPanel.setBounds(20, 120, 600, 40);
        add(buttonPanel);

        JPanel renamePanel = new JPanel();
        renamePanel.setLayout(null);
        renameLabel.setBounds(10, 10, 60, 20);
        renameTextField.setBounds(65, 10, 100, 20);
        renameButton.setBounds(170, 10, 120, 20);

        renamePanel.add(renameLabel);
        renamePanel.add(renameTextField);
        renamePanel.add(renameButton);

        renamePanel.setBounds(130, 170, 600, 40);
        add(renamePanel);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuButton.setBounds(10, 10, 120, 20);

        menuPanel.add(menuButton);

        menuPanel.setBounds(430, 300, 600, 100);
        add(menuPanel);
        repaint();
    }

    public void addActionListeners(ActionListener listener) {
        nextCycleButton.addActionListener(listener);
        playButton.addActionListener(listener);
        feedButton.addActionListener(listener);
        healButton.addActionListener(listener);
        renameButton.addActionListener(listener);
        menuButton.addActionListener(listener);
    }

    // Game Section

    private Pet pet;
    private int cycle;
    private int year;
    private int totalCycles;
    public void setData(Pet pet, int cycle, int totalCycles, int year) {
        this.pet = pet;
        this.cycle = cycle;
        this.year = year;
        this.totalCycles = totalCycles;

        updateLabels();
    }

    // Increase the cycle by one
    public boolean nextCycle() {
        pet.tick();
        cycle++;
        totalCycles++;
        if (cycle == 10 || (pet.getHatchTimeLeft() == 0 && pet.getType() == PetType.Egg)) {
            cycle = 0;
            year++;
            if (pet.getType() == PetType.Egg) {
                pet = pet.hatch();
            } else {
                pet.increaseAge();
            }
        }
        if (pet.endScenario()) {
            // Game ended
            return false;
        }
        updateLabels();
        return true;
    }

    public Object[] getData() {
        return new Object[]{pet, cycle, totalCycles, year};
    }

    public String getNewName() {
        return renameTextField.getText();
    }

    public void clearFields() {
        renameTextField.setText("");
    }

    public void renamePet(String name){
        pet.setName(name);
        updateLabels();
    }

    // Interaction Events

    // Play
    public void play() {
        pet.play();
        updateLabels();
    }

    // Feed
    public void feed() {
        pet.feed();
        updateLabels();
    }

    // Heal
    public void heal() {
        pet.heal();
        updateLabels();
    }

    // Repaints the panel with the current data
    public void updateLabels() {
        this.cycleLabel.setText("Cycle: " + cycle);
        this.yearLabel.setText("Year: " + year);

        this.nameLabel.setText("Name: " + pet.getName());
        this.ageLabel.setText("Age: " + pet.getAge());
        this.typeLabel.setText("Type: " + pet.getType());

        this.happinessLabel.setText("Happiness: " + pet.getHappiness());
        this.hungerLabel.setText("Hunger: " + pet.getHunger());
        this.healthLabel.setText("Health: " + pet.getHealth());

        if (pet.getType() == PetType.Egg) {
            playButton.setEnabled(false);
            feedButton.setEnabled(false);
            healButton.setEnabled(false);
        } else {
            playButton.setEnabled(true);
            feedButton.setEnabled(true);
            healButton.setEnabled(true);
        }

        updateUI();
    }

}
