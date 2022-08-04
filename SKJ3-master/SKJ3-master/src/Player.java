//this class represents server client

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Player{
    //communication
    BufferedReader in;
    PrintWriter out;
    private ClientHandler handler;


    private static int idCounter = 0;
    private int id;
    private Socket socket;

    Player(Socket socket) {
        this.socket = socket;
        this.id = idCounter + 1;
        idCounter ++;

        try {
            in = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
            out = new PrintWriter(this.getSocket().getOutputStream(), true);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.id + " - " + socket.getInetAddress() + ":" + socket.getLocalPort();
    }

    void disconnect(){
        log("disconnecting client " + toString());
        Main.activeClients.remove(this);
        Game.matchMaking.remove(this);
        try {
            socket.close();
        } catch (IOException e) {
            log("disconnecting failed");
            e.printStackTrace();
        }
    }

    void send(String message){
        out.println(message);
    }

    String receive(){
        String message;
        try {
            message = in.readLine();
            return message;
        } catch (IOException e) {
            disconnect();
            e.printStackTrace();
            return null;
        }
    }

    private void log(String message){
        SimpleDateFormat sdf =  new SimpleDateFormat("DD-MM-YY HH:mm:ss");
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "][" + socket.getLocalPort() + "]" + message);
    }

    Socket getSocket() {
        return socket;
    }

    int getId() {
        return id;
    }

    void setHandler(ClientHandler handler) {
        this.handler = handler;
    }

    ClientHandler getHandler() {
        return handler;
    }

}