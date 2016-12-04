package com.calderonyoungstrom.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created on 12/4/16.
 */
public class LoginForm extends JFrame {
    private JPanel panel1;
    private JButton submitButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        super("Login Form");
        initialize();
    }

    /**
     * Initializes the LoginForm
     */
    private void initialize(){
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login button clicked");
                System.out.println("Username: " + usernameField.getText());
                System.out.print("Password: ");
                System.out.println(passwordField.getPassword());
            }
        });
    }
}
