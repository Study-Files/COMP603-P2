package project2.GUI;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Set;

public class MenuPanel extends JPanel {

    private final JList<String> petsList;


    private final JTextField newPetNameField;
    private final JButton newPetButton;

    private final JButton selectPetButton;
    private final JButton deletePetButton;

    private final JButton quitButton;
    private final JButton logoutButton;

    public MenuPanel() {
        setLayout(null);
        JLabel petsLabel = new JLabel("Pets");
        petsList = new JList<>();
        JScrollPane scrollPane = new JScrollPane();
        JLabel newPetLabel = new JLabel("New Pet Name");
        newPetNameField = new JTextField(10);
        newPetButton = new JButton("Create Pet");
        selectPetButton = new JButton("Select Pet");
        deletePetButton = new JButton("Delete Pet");
        quitButton = new JButton("Quit");
        logoutButton = new JButton("Logout");

        // Put pet label above list
        JPanel petsPanel = new JPanel();
        petsPanel.setLayout(null);
        petsLabel.setBounds(10, 10, 100, 20);

        // Create scroll pane for list
        scrollPane.setViewportView(petsList);
        scrollPane.createVerticalScrollBar();
        scrollPane.setBounds(10, 40, 200, 200);

        // Add to panel
        petsPanel.add(petsLabel);
        petsPanel.add(scrollPane);
        petsPanel.setBounds(10, 10, 200, 200);
        add(petsPanel);

        // Put new pet field and button next to list and delete pet button newPetButton
        JPanel petPanel = new JPanel();
        petPanel.setLayout(null);

        // Set Layout
        newPetLabel.setBounds(10, 10, 100, 20);
        newPetNameField.setBounds(10, 40, 100, 20);
        newPetButton.setBounds(120, 40, 100, 20);
        selectPetButton.setBounds(10, 70, 100, 20);
        deletePetButton.setBounds(10, 100, 100, 20);

        petPanel.add(newPetLabel);
        petPanel.add(newPetNameField);
        petPanel.add(newPetButton);
        petPanel.add(selectPetButton);
        petPanel.add(deletePetButton);
        petPanel.setBounds(220, 10, 300, 200);
        add(petPanel);


        // Put quit and logout buttons centered at bottom of screen
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);

        // Set Layout
        quitButton.setBounds(0, 10, 100, 20);
        logoutButton.setBounds(110, 10, 100, 20);

        buttonPanel.add(quitButton);
        buttonPanel.add(logoutButton);
        buttonPanel.setBounds(150, 250, 400, 50);
        add(buttonPanel);

    }

    public void addActionListeners(ActionListener listener) {
        newPetButton.addActionListener(listener);
        selectPetButton.addActionListener(listener);
        deletePetButton.addActionListener(listener);
        quitButton.addActionListener(listener);
        logoutButton.addActionListener(listener);
    }

    public void addPetsList(Set<String> pets) {
        petsList.setListData(pets.toArray(String[]::new));
    }

    public String getNewPetName() {
        return newPetNameField.getText();
    }

    public String getSelectedPet() {
        return petsList.getSelectedValue();
    }

    public void clearFields() {
        newPetNameField.setText("");
        petsList.clearSelection();
    }
}
