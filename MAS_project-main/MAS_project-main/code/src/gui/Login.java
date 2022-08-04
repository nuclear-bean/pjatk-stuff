package gui;

import app.database.DatabaseConnector;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

@SuppressWarnings("deprecation")
public class Login implements Runnable{
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton zalogujButton;
    private JPanel mainPanel;
    JFrame frame;

    private boolean authorized = false;

    private void addListeners() {
        zalogujButton.addActionListener(e -> {
            if(passwordField.getText().isBlank() || loginField.getText().isBlank()){
                JOptionPane.showMessageDialog(null,"Please fill all fields!");
                return;
            }

            try {
                authorized = DatabaseConnector.authorize(loginField.getText(),passwordField.getText());

                // authorization failed
                if(!authorized) {
                    JOptionPane.showMessageDialog(null, "Incorrect credentials!", "Login failed", JOptionPane.WARNING_MESSAGE);
                    passwordField.setText("");
                }
                else {
                    frame.setVisible(false);
                    frame.dispose();
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null,"login failed: " + e1.getMessage(),"Login error",JOptionPane.WARNING_MESSAGE);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 10)
                    zalogujButton.doClick();
            }
        });
    }

    @Override
    public void run() {
        frame = new JFrame("Logowanie użytkownika");
        frame.add(mainPanel);
        frame.setSize(400,300);
        frame.setAlwaysOnTop(false);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //binding
        addListeners();

        frame.setVisible(true);

        while (frame.isVisible() && !authorized) {
            try {
                //noinspection BusyWait
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isAuthorized() {
        return authorized;
    }
}
