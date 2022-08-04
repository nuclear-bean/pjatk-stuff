package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPListener extends Thread {
    private final ServerSocket serverSocket;
    public TCPListener(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            Socket client = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);

            //receive the string, reverse it and send it back to where it came from
            String line = reader.readLine();
            StringBuilder builder = new StringBuilder(line);
            builder.reverse();
            writer.println(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
