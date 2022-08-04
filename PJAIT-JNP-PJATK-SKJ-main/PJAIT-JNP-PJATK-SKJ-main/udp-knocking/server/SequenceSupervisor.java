package server;

import java.util.ArrayList;
import java.util.HashMap;

public class SequenceSupervisor {
    private static final long CLIENT_RESET_INTERVAL = 30 * 1000;    // time of inactivity after which client sequence will be reset
    // SEQUENCE
    public static int [] sequence;

    public static HashMap<String,Long> clientLastConnection = new HashMap<>();

    // key: client address (ip:port) ; value: sequence list
    private static final HashMap<String, ArrayList<Integer>> clientSequences = new HashMap<>();

    // use this method when client knocked on port
    public static void addSequence(String clientAddress, int port){
        if(!clientSequences.containsKey(clientAddress))
            clientSequences.put(clientAddress, new ArrayList<>());
        clientSequences.get(clientAddress).add(port);

        // update last connection time
        clientLastConnection.put(clientAddress,System.currentTimeMillis());
    }

    public static boolean verifySequence(String clientAddress){
        ArrayList<Integer> clientKnockOrder = clientSequences.get(clientAddress);

        if(clientKnockOrder.size() != sequence.length)
            return false;

        for (int i = 0; i < clientKnockOrder.size(); i++) {
            if (clientKnockOrder.get(i) != sequence[i])
                return false;
        }
        return true;
    }

    public static ArrayList<Integer> getClientSequence(String clientAddress){
        return clientSequences.get(clientAddress);
    }

    public static void resetClientSequence(String clientAddress){
        log("resetting sequence for " + clientAddress);
        clientSequences.remove(clientAddress);
    }

    @SuppressWarnings("BusyWait")
    public static void startSequenceResetThread(){
        Thread resetThread = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(5000);
                    for (String clientAddress : clientLastConnection.keySet()) {
                        long lastConnection = clientLastConnection.get(clientAddress);
                        long now = System.currentTimeMillis();
                        if(!clientSequences.get(clientAddress).isEmpty() && lastConnection + CLIENT_RESET_INTERVAL < now) {
                            log("No data received from client " + clientAddress + " for " + CLIENT_RESET_INTERVAL + " milliseconds. Sequence will be reset");
                            resetClientSequence(clientAddress);
                        }
                    }

                } catch (Exception e){
                    //noinspection UnnecessaryContinue
                    continue;
                }
            }
        });
                resetThread.start();
    }

    private static void log(String m){
        System.out.println("[Server Sequence Supervisor]: " + m);
    }
}
