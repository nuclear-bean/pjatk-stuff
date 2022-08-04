import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Login {
    private JPanel loginPanel;
    private JTextField loginTextField;
    private JPasswordField passwordPasswordField;
    private JButton logInButton;
    private JButton registerButton;
    private JButton quitButton;
    private JLabel status;
    private JLabel header;
    private String username;

    Login() {
        showLogin();
    }

    private void register() {
        status.setText("");
        registerButton.setVisible(false);

        header.setText("Register to JMS Chat");
        logInButton.setText("Create Account");

        for (ActionListener actionListener : logInButton.getActionListeners()) {
            logInButton.removeActionListener(actionListener);
        }
        logInButton.addActionListener(e -> {
            String username = loginTextField.getText();
            char [] pass = passwordPasswordField.getPassword();
            if(Main.passwords.containsKey(username))
                status.setText("username taken!");
            else {
                Main.addUser(username, pass);
                //back to normal
                status.setText("");
                logInButton.setText("Log In");
                registerButton.setVisible(true);
                header.setText("Log in to JMS chat");
                showLogin();
            }
        });

    }

    private void showLogin(){
        for (ActionListener actionListener : logInButton.getActionListeners()) {
            logInButton.removeActionListener(actionListener);
        }

        logInButton.addActionListener(e -> {
            if(verifyLogin())
                Main.logIn();
            else {
                status.setForeground(Color.RED);
                status.setText("INCORRECT USERNAME / PASSWORD -> TRY AGAIN");
            }
        });
        quitButton.addActionListener(e -> System.exit(0));
        registerButton.addActionListener(e -> {register();});
    }

    private boolean verifyLogin() {
        username = loginTextField.getText();
        char [] password = passwordPasswordField.getPassword();

        char [] correct = Main.passwords.get(username);

        return Arrays.equals(password,correct);
    }

    JPanel getLoginPanel() {
        return loginPanel;
    }

    public String getUsername() {
        return username;
    }
}
