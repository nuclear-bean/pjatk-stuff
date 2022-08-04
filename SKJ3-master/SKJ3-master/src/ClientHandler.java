import org.omg.PortableServer.THREAD_POLICY_ID;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientHandler implements Runnable {
    //client messages
    private static final String CLIENT_LOGOUT = "LOGOUT";
    private static final String CLIENT_PLAY   = "PLAY";
    private static final String CLIENT_LIST   = "LIST";

    private Player client;
    private int id;
    private boolean active;

    ClientHandler(Player client, int id){
        this.client = client;
        this.id = id;
        active = true;
        client.send(String.valueOf(id));
    }

    @Override
    public void run() {
        //listening for client commands
        log("new client connected!");
        log("client with id " + client.toString());

        //add client to active client list
        Main.activeClients.add(client);


        while (active){       //client handling loop

            //wait for user choice
            log("waiting for user's choice");
            String choice = client.receive();
            log("received: " + choice + " from client with id " + client.getId());

            try {
                switch (choice){
                    case CLIENT_LOGOUT  :
                        client.disconnect();
                        active=false;
                        break;

                    case CLIENT_PLAY    :
                        Game.matchMaking.add(client);        //add client to list of players waiting for a game
                        if(Game.matchMaking.size()%2 == 0 && Game.matchMaking.size() > 1) {
                            Thread thread = new Thread(() -> {
                                try {
                                    new Game();
                                } catch (IOException e) {
                                    log("client disconnected");
                                    active = false;
                                    client.disconnect();
                                }
                            });
                            thread.start();
                        }
                        waitRunnable();
                        break;

                    case CLIENT_LIST    :
                        sendList();
                        break;

                    default :
                        log("wrong choice code received. Closing connection");
                        active = false;
                        client.disconnect();
                }
            }
            catch (NullPointerException e){
                log("client disconnected");
                active = false;
                client.disconnect();
            }
        }
        Main.activeClients.remove(client);
    }

    private synchronized void waitRunnable() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendList() {
        client.send(String.valueOf(Main.activeClients.size()));
        for (Player player : Main.activeClients) {
            client.send(player.getId() + "-" + player.getSocket().getInetAddress() + ':' + player.getSocket().getPort());
        }

        log("sent list to client");
    }

    private void log(String message){
        SimpleDateFormat sdf =  new SimpleDateFormat("DD-MM-YY HH:mm:ss");
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "][Hndl]: " + message);
    }

    Player getClient() {
        return client;
    }
}
