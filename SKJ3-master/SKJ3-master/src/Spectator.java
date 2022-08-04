import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Spectator {
    private static final int UDP_PORT = 4445;

    public static void main(String[] args) {
        System.out.println("spectating at port: " + UDP_PORT);

        //starting UDP socket
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException e) {
            e.printStackTrace();
        }


        //noinspection InfiniteLoopStatement
        while (true){
            try {
                byte[] buf = new byte[61];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                byte [] data = packet.getData();

                String message = "";
                for (byte b:data) {
                    message += (char)b;
                }
                String [] tmp = message.split("# ");
                String player1 = tmp[0];
                String p1id = player1.substring(0,1);
                String p1ip = player1.substring(player1.indexOf('/') - 1);


                String player2 = tmp[1];
                String p2id = player2.substring(0,1);
                String p2ip = player2.substring(player2.indexOf('/') - 1);

                String board   = tmp[2];

                System.out.println("GAME BETWEEN PLAYER " + p1id + " (" + p1ip + ") AND PLAYER   "
                + p2id + " (" + p2ip  + "):");
                boardToString(board);           //printing board
            }
            catch (IOException e){
                System.out.println("IO Exception was thrown");
            }
        }
    }

    private static void boardToString(String board){
        //parsing input
        String input = board;
        String [] tmp = input.split(" ");
        int [] board1 = new int[9];
        for (int i = 0; i < tmp.length; i++) {
            board1 [i] = Integer.parseInt(tmp[i]);
        }

        //printing board
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            System.out.print('|');
            for (int j = 0; j < 3; j++) {
                switch (board1[counter]) {
                    case 0 :
                        System.out.print("   |");
                        break;
                    case 1 :
                        System.out.print(" O |");
                        break;
                    case 2:
                        System.out.print(" X |");
                        break;
                }
                counter++;
            }
            System.out.println();
        }
    }
}
