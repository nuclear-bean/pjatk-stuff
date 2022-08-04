import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


@SuppressWarnings({"FieldCanBeLocal"})
class Broadcaster {
    private static DatagramSocket socket;
    private static InetAddress address;

    static void broadcast(Game game) {
        try {
            address = InetAddress.getByName("255.255.255.255");
            String broadcastMessage;
            broadcastMessage = game.getPlayer1().toString() + " # ";
            broadcastMessage += game.getPlayer2().toString() + " # ";

            int [] tmp = game.getBoard();
            String board = "";
            for (int i = 0; i < tmp.length ; i++) {
                board += String.valueOf(tmp[i]) + " ";
            }

            broadcastMessage += board;


            socket = new DatagramSocket();
            socket.setBroadcast(true);

            byte[] buffer = broadcastMessage.getBytes();

            DatagramPacket packet
                    = new DatagramPacket(buffer, buffer.length, address, 4445);
            socket.send(packet);
            socket.close();
        }
        catch (IOException e){
            System.out.println("an error with broadcaster occurred.");
        }
    }
}
