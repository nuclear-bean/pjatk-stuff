package client;

import javax.xml.crypto.Data;
import javax.xml.xpath.XPathFunctionResolver;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {
    // CONFIGURATION
    private static final int RESPONSE_PORT = 60000;
    private static final int TIMEOUT = 2000;            // server response timeout in milliseconds
    private static final String message = "Ala ma kota";    // this is the message that will be sent to server
    private static final ArrayList<Integer> portSequence = new ArrayList<>();   // knocking sequence will be stored here
    private static InetAddress SERVER_ADDRESS;

    private static DatagramSocket socket;

    public static void main(String[] args) throws IOException, InterruptedException {
        // start socket
        socket = new DatagramSocket();

        // get server address from args array
        SERVER_ADDRESS = InetAddress.getByName(args[0]);

        // get port sequence from args array
        for (int i = 1; i < args.length; i++) {
            int port = Integer.parseInt(args[i]);
            portSequence.add(port);
        }

        // knock on ports
        for (Integer port : portSequence) {
            knockOnPort(port);
            Thread.sleep(300);
        }

        try {
            int portNumber = Integer.parseInt(receive().trim());
            log("Authorized successfully. Received TCP port number: " + portNumber);

            // connect to server over TCP
            String response = reverseStringOverTcp(portNumber);
            log("received server response: " + response);
        } catch (SocketTimeoutException e){
            log("Connection timed out, server did not respond. ");
        }
    }

    private static String receive() throws IOException {
        DatagramSocket socket = new DatagramSocket(RESPONSE_PORT);
        socket.setSoTimeout(TIMEOUT);                               //set socket timeout

        log("waiting for server response");

        byte[] buffer = new byte[256];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        socket.receive(request);
        return new String(request.getData());
    }

    public static void knockOnPort(int port) throws IOException {
        log("Knocking on port " + port);
        byte[] buffer = new byte[256];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, SERVER_ADDRESS, port);
        socket.send(request);
    }


    public static String reverseStringOverTcp(int port) throws IOException {
        // connect to server
        Socket socket = new Socket(SERVER_ADDRESS,port);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

        // send
        writer.println(message);

        // receive
        return reader.readLine();
    }
    
    private static void log(String m){
        System.out.println("[CLIENT]: " + m);
    }
}
