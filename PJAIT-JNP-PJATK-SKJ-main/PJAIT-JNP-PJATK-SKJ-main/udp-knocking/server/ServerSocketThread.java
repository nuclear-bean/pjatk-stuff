package server;

import java.io.IOException;
import java.net.*;
import java.util.Random;

public class ServerSocketThread extends Thread{
    private static final int CLIENT_RESPONSE_PORT = 60000;
    private final int port;
    DatagramSocket datagramSocket;
    ServerSocket tcpSocket;
    boolean running;

    public ServerSocketThread(int port) throws SocketException {
        this.port = port;
        this.datagramSocket = new DatagramSocket(port);
        this.running = true;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        try {
            while (running){
                byte[] buffer = new byte[256];     // create buffer for received message\
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                datagramSocket.receive(packet);    // receive incoming data

                InetAddress address = packet.getAddress();
                packet = new DatagramPacket(buffer, buffer.length, address, port);

                // get sender's data
                String clientAddress =  address.getHostAddress();
                int knockPort = packet.getPort();

                // if message content is equal to preset knock message
                log("knock received from: " + clientAddress + " on port " + knockPort + ". Adding to knock sequence.");
                SequenceSupervisor.addSequence(clientAddress,knockPort);

                if(SequenceSupervisor.verifySequence(clientAddress)){
                    log("client " + address + " just sent a correct sequence!");
                    Thread.sleep(300);                  // give client some time to start his socket
                    sendPortNumberToClient(address);

                    // reset client sequences
                    SequenceSupervisor.resetClientSequence(clientAddress);
                }
                else {
                    log("client " + address + " sequence so far: " + SequenceSupervisor.getClientSequence(clientAddress));
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates and sends the message with TCP port number to a client who authorized successfully.
     * TCP port included in the message will be used for further communication with authorized client
     * @param address InetAddress of client that just authorized successfully
     */
    private void sendPortNumberToClient(InetAddress address) throws IOException {
        tcpSocket = createServerSocket();
        new TCPListener(tcpSocket).start();
        byte [] buffer = String.valueOf(tcpSocket.getLocalPort()).getBytes();

        DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, CLIENT_RESPONSE_PORT);
        datagramSocket.send(request);
    }

    private ServerSocket createServerSocket() {
        Random rand = new Random();
        int portNumber = rand.nextInt(10000)+50000;     // pick random port
        try {
            return new ServerSocket(portNumber);
        } catch (IOException e){
            return createServerSocket();
        }
    }

    private void log(String message){
        System.out.println("[Server Socket (" + port + ")]: " + message);
    }
}
