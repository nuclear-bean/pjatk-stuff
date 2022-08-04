import javax.swing.*;

public class enterText implements Runnable {
    private JButton GOButton;
    JTextArea textArea1;
    private JButton leaveButton;
    private JPanel panel;

    JFrame frame = new JFrame();

    @Override
    public void run() {
        GOButton.addActionListener(e -> {
            Main.text = textArea1.getText();
                    frame.setVisible(false);
            frame.dispose();

        });
        leaveButton.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
        });

        frame.add(panel);
        frame.setSize(400,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
