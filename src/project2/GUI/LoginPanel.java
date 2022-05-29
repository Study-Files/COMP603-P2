package project2.GUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;

    public LoginPanel() {

        setLayout(null);
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(10);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        // Set layout
        usernameLabel.setBounds(10, 10, 80, 25);
        usernameField.setBounds(100, 10, 160, 25);
        passwordLabel.setBounds(10, 40, 80, 25);
        passwordField.setBounds(100, 40, 160, 25);
        loginButton.setBounds(10, 80, 100, 25);
        registerButton.setBounds(160, 80, 100, 25);

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        loginPanel.setBounds(10, 10, 300, 150);
        add(loginPanel);
    }

    public void addActionListeners(ActionListener listener) {
        loginButton.addActionListener(listener);
        registerButton.addActionListener(listener);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }


}
