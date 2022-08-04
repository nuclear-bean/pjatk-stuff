import javax.swing.*;

/**
 * @author Jakub Partyka s18830
 */
public class Updater extends SwingWorker {
    int status = -1;
    private Grid mainGrid;
    int sleep = 500;

    Updater(Grid mainGrid){
        this.mainGrid = mainGrid;
    }

    @Override
    protected Object doInBackground() throws Exception {
        //noinspection InfiniteLoopStatement
        while (true) {
                mainGrid.createNodeGrid();
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

}
