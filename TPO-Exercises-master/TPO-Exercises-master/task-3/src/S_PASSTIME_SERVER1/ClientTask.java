/**
 *
 *  @author Partyka Jakub S18830
 *
 */

package S_PASSTIME_SERVER1;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientTask implements Runnable {
    private Client client;
    private List<String> reqList;
    private boolean showRes;

    public ClientTask(Client client, List<String> reqList, boolean showRes) {
        this.client = client;
        this.reqList = reqList;
        this.showRes = showRes;
    }

    @Override
    public void run() {

    }

    public static ClientTask create(Client client, List<String> reqList, boolean showRes) {
        return new ClientTask(client,reqList,showRes);
    }

    public String get() throws InterruptedException, ExecutionException {
        //todo return log
        return null;
    }

}
