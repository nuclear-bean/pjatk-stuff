import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//server
public class Main {

    private static int id = 0;
    static List<Player> activeClients   = new ArrayList<>();

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        log("server start");

        //accepting clients
        try {
            while (true) {
                ClientHandler newHandler = new ClientHandler(acceptClient(), nextId());
                newHandler.getClient().setHandler(newHandler);
                Thread thread = new Thread(newHandler);
                thread.start();                                 //starting new client handler thread
            }
        }
        catch (Exception e){
            log("server end");
            e.printStackTrace();
        }
    }

    private static Player acceptClient(){
        while (true)
            try (ServerSocket serverSocket = new ServerSocket(9090, 0, InetAddress.getByName("localhost"))){
                log("Server waiting for client");
                return new Player(serverSocket.accept());
            } catch (IOException e) {

                e.printStackTrace();
            }
    }

    private static void log(String message){
        SimpleDateFormat sdf =  new SimpleDateFormat("DD-MM-YY HH:mm:ss");
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "][Main]: " + message);
    }

    private static int nextId(){
        id ++;
        return id;
    }
}
