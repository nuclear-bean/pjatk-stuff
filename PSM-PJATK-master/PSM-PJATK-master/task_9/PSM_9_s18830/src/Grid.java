import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 * @author Jakub Partyka s18830
 */

class Grid extends JPanel {
    private Color[][]  myColors;
    private JLabel[][] myLabels;

    static Color DARK_GREEN = new Color(0, 0, 0);

    private static Node [] [] nodeGrid;

    private static int[][] NEIGHBOURS_OF_CELL = {
            {-1, 1}, {0, 1}, {1, 1},
            {-1, 0},         {1, 0},
            {-1, -1}, {0, -1}, {1, -1}};

    Grid(int rows, int cols, int cellWidth) {

        myColors = new Color[rows][cols];
        myLabels = new JLabel[rows][cols];

        MyMouseListener myListener = new MyMouseListener(this);
        this.setFocusable(true);

        Dimension labelPrefSize = new Dimension(cellWidth, cellWidth);

        setLayout(new GridLayout(rows, cols));

        for (int row = 0; row < myLabels.length; row++) {
            for (int col = 0; col < myLabels[row].length; col++) {
                JLabel myLabel = new JLabel();
                myLabel.setOpaque(true);
                Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
                myLabel.setBorder(border);

                myColors[row][col] = Color.WHITE;

                myLabel.setBackground(myColors[row][col]);
                myLabel.addMouseListener(myListener);
                myLabel.setPreferredSize(labelPrefSize);

                add(myLabel);
                myLabels[row][col] = myLabel;
            }
        }
    }

    private Color[][] getMyColors() {
        return myColors;
    }

    private static boolean isNodeAlive(int x, int y) {
        try {
            return nodeGrid[x][y].isAlive;
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    void createNodeGrid() {
        nodeGrid = new Node[myColors.length][myColors[0].length];
        Color[][] myColors = this.getMyColors();

        for (int row = 0; row < myColors.length; row++) {
            for (int col = 0; col < myColors[row].length; col++) {
                if (myColors[row][col] == Color.WHITE)
                    nodeGrid[row][col] = new Node(false);
                if (myColors[row][col] == DARK_GREEN)
                    nodeGrid[row][col] = new Node(true);
            }
        }

        for (int i = 0; i < nodeGrid.length; i++) {
            for (int j = 0; j < nodeGrid[i].length; j++) {
                int aliveCellCount = 0;
                nodeGrid[i][j].willBeBorn = false;
                for (int [] corner : NEIGHBOURS_OF_CELL) {
                        if (isNodeAlive(i+corner[0],j + corner[1]) && nodeGrid[i + corner[0]][j + corner[1]].isAlive)
                            aliveCellCount++;
                }
                if (Main.right.contains(aliveCellCount) && !nodeGrid[i][j].isAlive)
                    nodeGrid[i][j].willBeBorn = true;

                if ((Main.left.contains(aliveCellCount)) && nodeGrid[i][j].isAlive)
                    nodeGrid[i][j].willBeBorn = true;
            }
        }

        //UPDATE NODE DISPLAY
        for (int i = 0; i < nodeGrid.length; i++) {
            for (int j = 0; j < nodeGrid[i].length; j++) {
                if (nodeGrid[i][j].willBeBorn) {
                    myColors[i][j] = DARK_GREEN;
                    myLabels[i][j].setBackground(DARK_GREEN);
                }
                else {
                    myColors[i][j] = Color.WHITE;
                    myLabels[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    void labelPressed(JLabel label, boolean nodeIsActive) {
        for (int row = 0; row < myLabels.length; row++) {
            for (int col = 0; col < myLabels[row].length; col++) {
                if (label == myLabels[row][col]) {
                    if(nodeIsActive) {
                        myColors[row][col] = DARK_GREEN;
                        myLabels[row][col].setBackground(DARK_GREEN);
                    }
                    else {
                        myColors[row][col] = Color.WHITE;
                        myLabels[row][col].setBackground(Color.WHITE);
                    }
                }
            }
        }
    }
}