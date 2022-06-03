package project2.GUI;

import project2.Database.DatabaseController;
import project2.Database.DatabasePets;
import project2.Database.DatabaseUser;
import project2.Pets.Pet;
import project2.Pets.Types.Egg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class GUIController extends JFrame implements ActionListener {

    private String state;

    private String username;
    Pet pet;

    HashMap<String, Pet> pets = new HashMap<>();

    private final DatabaseController database;

    private final LoginPanel loginPanel;
    private final MenuPanel menuPanel;
    private final GamePanel gamePanel;
    private final GameOverPanel gameOverPanel;

    public GUIController(LoginPanel loginPanel, MenuPanel menuPanel, GamePanel gamePanel, GameOverPanel gameOverPanel) {
        super("Project 2");

        database = new DatabaseController();

        state = "login";
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.loginPanel = loginPanel;
        this.menuPanel = menuPanel;
        this.gamePanel = gamePanel;
        this.gameOverPanel = gameOverPanel;
        addListeners();

        add(loginPanel);
        setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (state.equals("game")) {
                    // Save data before closing window
                    Object[] data = gamePanel.getData();
                    pet = (Pet) data[0];
                    System.out.println(data[1]);
                    DatabasePets.updateCycleData(database.getConnection(), username, pet.getName(), (int) data[1], (int) data[2], (int) data[3]);
                    DatabasePets.savePet(database.getConnection(), username, pet);
                    JOptionPane.showMessageDialog(e.getComponent(),
                            "Saving game...",
                            null, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void addListeners() {
        loginPanel.addActionListeners(this);
        menuPanel.addActionListeners(this);
        gamePanel.addActionListeners(this);
        gameOverPanel.addActionListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Login Buttons
        if (state.equals("login")) {
            // Login Button
            if (e.getActionCommand().equals("Login")) {
                username = loginPanel.getUsername();
                String password = loginPanel.getPassword();
                if (username.length() < 1 || password.length() < 1) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a username and password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (username.length() > 255 || password.length() > 255) {
                    JOptionPane.showMessageDialog(this,
                            "Username and password must be less than 255 characters",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Check if username and password are valid
                if (DatabaseUser.login(database.getConnection(), username, password)) {
                    state = "menu";
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid username or password.\nAre you trying to make a new account?",
                            "Error", JOptionPane.QUESTION_MESSAGE);
                    return;
                }
            }
            // Register Button
            if (e.getActionCommand().equals("Register")) {
                if (loginPanel.getUsername().length() < 1 || loginPanel.getPassword().length() < 1) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a username and password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (loginPanel.getUsername().length() > 255 || loginPanel.getUsername().length() > 255) {
                    JOptionPane.showMessageDialog(this,
                            "Username and password must be less than 255 characters",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!loginPanel.getUsername().matches("[a-zA-Z0-9]+")) {
                    JOptionPane.showMessageDialog(this,
                            "Username must be alphanumeric",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Check if username is valid
                if (DatabaseUser.register(database.getConnection(), loginPanel.getUsername(), loginPanel.getPassword())) {
                    username = loginPanel.getUsername();
                    state = "menu";
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Username already exists.\nPlease choose another username.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }

            // If moving to menu, get all pets from database
            if (state.equals("menu")) {
                // Retrieve all pets from database
                pets.clear();
                for (Pet pet : DatabasePets.getPets(database.getConnection(), username)) {
                    pets.put(pet.getName(), pet);
                }
                menuPanel.addPetsList(pets.keySet());

                // Load menu panel
                loginPanel.clearFields();
                getContentPane().removeAll();
                add(menuPanel);
                revalidate();
                repaint();
            }
        }
        // Menu Buttons
        if (state.equals("menu")) {
            // Logout Button
            if (e.getActionCommand().equals("Logout")) {
                menuPanel.clearFields();
                // Load login panel
                getContentPane().removeAll();
                add(loginPanel);
                revalidate();
                repaint();
                state = "login";
            }
            // Quit Button
            if (e.getActionCommand().equals("Quit")) {
                System.exit(0);
            }
            // Create Pet Button
            if (e.getActionCommand().equals("Create Pet")) {
                String name = menuPanel.getNewPetName();
                if (name.length() < 1) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a name for your pet.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (name.length() > 255) {
                    JOptionPane.showMessageDialog(this,
                            "Pet name must be less than 255 characters.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (pets.containsKey(name)) {
                    JOptionPane.showMessageDialog(this,
                            "A pet with that name already exists.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // check if name contains anyting other than letters, numbers, and spaces
                if (!name.matches("[a-zA-Z0-9 ]+")) {
                    JOptionPane.showMessageDialog(this,
                            "Pet names can only contain letters, numbers, and spaces.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pet = new Egg(name);
                gamePanel.setData(pet, 0, 0, 0);
                state = "game";
            }
            // Select Pet Button
            if (e.getActionCommand().equals("Select Pet")) {
                String name = menuPanel.getSelectedPet();
                if (name == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please select a pet.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pet = pets.get(name);
                // Load cycle data for pet
                int[] cycles = DatabasePets.getCycleData(database.getConnection(), username, pet.getName());
                gamePanel.setData(pet, cycles[0], cycles[1], cycles[2]);
                state = "game";
            }
            // Delete Pet Button
            if (e.getActionCommand().equals("Delete Pet")) {
                String name = menuPanel.getSelectedPet();
                if (name == null) {
                    JOptionPane.showMessageDialog(this,
                            "Please select a pet.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int result = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete " + name + "?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // Delete pet from database
                    DatabasePets.deletePet(database.getConnection(), username, name);
                    pets.remove(name);
                    menuPanel.addPetsList(pets.keySet());
                }
            }

            // If moving to game load game panel
            if (state.equals("game")) {
                menuPanel.clearFields();
                getContentPane().removeAll();
                add(gamePanel);
                revalidate();
                repaint();
            }

        }
        // Game buttons
        if (state.equals("game")) {
            // Return to menu
            if (e.getActionCommand().equals("Menu")) {
                gamePanel.clearFields();
                Object[] data = gamePanel.getData();
                pet = (Pet) data[0];
                // Update database with pet info
                DatabasePets.updateCycleData(database.getConnection(), username, pet.getName(), (int) data[1], (int) data[2], (int) data[3]);
                DatabasePets.savePet(database.getConnection(), username, pet);
                state = "menu";
                JOptionPane.showMessageDialog(this,
                        "Saving game...",
                        null, JOptionPane.INFORMATION_MESSAGE);
            }
            // Rename Pet Button
            if (e.getActionCommand().equals("Rename")) {
                System.out.println("Rename");
                String name = gamePanel.getNewName();
                if (name.length() > 0) {
                    if (!name.matches("[a-zA-Z0-9 ]+")) {
                        JOptionPane.showMessageDialog(this,
                                "Pet names can only contain letters, numbers, and spaces.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (pets.containsKey(name)) {
                        JOptionPane.showMessageDialog(this,
                                "A pet with that name already exists.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String oldName = pet.getName();
                    // Rename pet in database
                    DatabasePets.renamePet(database.getConnection(), username, oldName, name);
                    // Update pets in menu
                    pets.remove(oldName);
                    pet.setName(name);
                    pets.put(name, pet);
                    gamePanel.renamePet(name);
                    gamePanel.clearFields();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a name.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            int totalCycles = 0;

            // Progress cycle
            if (e.getActionCommand().equals("Next Cycle")) {
                if (!gamePanel.nextCycle()) {
                    Object[] data = gamePanel.getData();
                    pet = (Pet) data[0];
                    totalCycles = (int) data[2];
                    state = "gameover";
                }
            }

            // Play with pet
            if (e.getActionCommand().equals("Play")) {
                gamePanel.play();
                if (!gamePanel.nextCycle()) {
                    Object[] data = gamePanel.getData();
                    pet = (Pet) data[0];
                    totalCycles = (int) data[2];
                    state = "gameover";
                }
            }

            // Feed pet
            if (e.getActionCommand().equals("Feed")) {
                gamePanel.feed();
                if (!gamePanel.nextCycle()) {
                    Object[] data = gamePanel.getData();
                    pet = (Pet) data[0];
                    totalCycles = (int) data[2];
                    state = "gameover";
                }
            }

            // Heal pet
            if (e.getActionCommand().equals("Heal")) {
                gamePanel.heal();
                if (!gamePanel.nextCycle()) {
                    Object[] data = gamePanel.getData();
                    pet = (Pet) data[0];
                    totalCycles = (int) data[2];
                    state = "gameover";
                }
            }

            if (state.equals("gameover")) {
                gamePanel.clearFields();
                DatabasePets.deletePet(database.getConnection(), username, pet.getName());
                // Set game over message
                gameOverPanel.setMessage(pet, totalCycles);

                // Load gameover panel
                getContentPane().removeAll();
                add(gameOverPanel);
                revalidate();
                repaint();
            }

            if (state.equals("menu")) {
                // Retrieve all pets from database
                pets.clear();
                for (Pet pet : DatabasePets.getPets(database.getConnection(), username)) {
                    pets.put(pet.getName(), pet);
                }
                menuPanel.addPetsList(pets.keySet());

                // Load menu panel
                getContentPane().removeAll();
                add(menuPanel);
                revalidate();
                repaint();
            }
        }

        // Game over Buttons
        if (state.equals("gameover")) {
            // Menu Button
            if (e.getActionCommand().equals("Menu")) {
                state = "menu";
            }
            // Exit Button
            if (e.getActionCommand().equals("Exit")) {
                System.exit(0);
            }

            if (state.equals("menu")) {
                // Retrieve all pets from database
                pets.clear();
                for (Pet pet : DatabasePets.getPets(database.getConnection(), username)) {
                    pets.put(pet.getName(), pet);
                }
                menuPanel.addPetsList(pets.keySet());

                // Load menu panel
                getContentPane().removeAll();
                add(menuPanel);
                revalidate();
                repaint();
            }
        }
    }
}
