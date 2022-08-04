import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * @author Jakub Partyka s18830
 */
public class Main {
    private static JFrame frame;
    private static JTextField input;
    private static JTextField color;
    static HashSet<Integer> left = new HashSet<>();
    static HashSet<Integer> right = new HashSet<>();

    private static void createAndShowGui() {
        final int GRID_SIZE = 20;
        int cellWidth = 20;

        Grid mainGrid = new Grid(GRID_SIZE, GRID_SIZE, cellWidth);

        //init frame
        initFrame();

        frame.setLayout(new BorderLayout());
        frame.add(mainGrid,BorderLayout.NORTH);

        input = new JTextField();
        input.setText("enter values here");

        color = new JTextField();
        color.setText("0 0 0");

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(input,BorderLayout.WEST);
        p.add(color,BorderLayout.EAST);

        input.setPreferredSize(new Dimension(200,30));
        color.setPreferredSize(new Dimension(200,30));

        frame.add(p,BorderLayout.CENTER);

        JButton startButton = new JButton();
        startButton.setText("START GAME");
        startButton.setPreferredSize(new Dimension(200,30));

        JButton colorButton = new JButton("SET COLOR");
        colorButton.setPreferredSize(new Dimension(200,30));

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.add(startButton,BorderLayout.WEST);
        p1.add(colorButton,BorderLayout.EAST);

        frame.add(p1,BorderLayout.SOUTH);

        Updater thread = new Updater(mainGrid);
        colorButton.addActionListener(e -> {
            if(thread.status != -1)
                return;
            try {
                String[] data = color.getText().split(" ");
                for (int i = 0; i < 3; i++) {
                    Grid.DARK_GREEN = new Color(Integer.parseInt(data[0]),
                            Integer.parseInt(data[1]),
                            Integer.parseInt(data[2]));
                }
            }
            catch (Exception e1){
                e1.printStackTrace();
            }
        });


        startButton.addActionListener(e -> {
            if (thread.status == -1) {       //start
                if(!getValues())
                    return;

                thread.status = 1;
                thread.execute();

                System.out.println("Thread started");
                startButton.setText("END GAME");
            }


            else if (thread.status == 0) {
                thread.status = 1;

                System.out.println("Thread resumed");
                startButton.setText("PAUSE GAME");
            }


            else if (thread.status == 1) {
                System.exit(1);
                thread.status = 0;

                System.out.println("Thread paused");
                startButton.setText("RESUME GAME");
            }
        });



        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static boolean getValues() {
        String [] data = input.getText().replaceAll(" ","").split("/");
        if(data.length != 2) {
            JOptionPane.showMessageDialog(null, "incorrect parameters");
            return false;
        }

        for (char c : data[0].toCharArray()) {
            try{
                left.add(Integer.parseInt(String.valueOf(c)));
            } catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "incorrect parameters");
                return false;
            }
        }

        for (char c : data[1].toCharArray()) {
            try{
                right.add(Integer.parseInt(String.valueOf(c)));
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "incorrect parameters");
                return false;
            }
        }

        return true;
    }

    private static void initFrame() {
        frame = new JFrame();
        frame.setTitle("The game of life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }
}