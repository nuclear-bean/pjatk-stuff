import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

@SuppressWarnings("FieldCanBeLocal")
public class Client{
    private static final String LOGOUT = "LOGOUT";
    private static final String PLAY   = "PLAY";
    private static final String LIST   = "LIST";

    private static PrintWriter out;
    private static BufferedReader in;

    private static int id;

    private static Socket socket;
    private static String server_address = "localhost";
    private static int server_port       = 9090;

    private static boolean gameInProgress = true;


    public static void main(String[] args) throws IOException {
        //connect to server and stop if failed. (stack trace will be printed)
        if(!connect()){
            log("connection unsuccessful");
            return;
        }

        //set up communication with server
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //getting id given by server
        id = Integer.parseInt(receive());
        System.out.println("your id is: " + id);

        String choice = LOGOUT;

        boolean keepAlive = true;
        while (keepAlive) {
        do {
                //ask user for action
                System.out.println("what would you like to do?");
                System.out.println("PLAY, LIST, LOGOUT" );
                System.out.print("> ");

                //read user choice
                try{
                    choice = new Scanner(System.in).nextLine().toUpperCase();
                }
                catch (Exception e){
                    disconnect();
                }
        } while ((!choice.equals(PLAY)) && (!choice.equals(LOGOUT) && (!choice.equals(LIST))));

        //send user choice to server
        send(choice);

            switch (choice) {
                case LOGOUT:
                    disconnect();
                    keepAlive = false;
                    break;
                case PLAY:
                    playGame();
                    break;
                case LIST:
                    receiveList();
                    break;
                default:
                    log("wrong choice code sent. Closing connection");
                    keepAlive = false;
                    disconnect();
            }
        }

        disconnect();
    }

    @SuppressWarnings("ConstantConditions")
    private static void playGame() {
        gameInProgress = true;
        boolean first;

        //wait for information if is first to start
        if(receive().equalsIgnoreCase("first")) {
            System.out.println("YOUR SYMBOL : O");
            first = true;
        }
        else {
            System.out.println("YOUR SYMBOL : X");
            first = false;
        }

        //make move or wait for turn
        if(first) {
            out.println(makeMove());
            waitForTurn();
        }
        else
            waitForTurn();

        if(!gameInProgress)
            return;

        gameInProgress = true;

        while (gameInProgress){
            out.println(makeMove());
            waitForTurn();
            if(!gameInProgress)
                return;
        }
    }

    private static void waitForTurn() {
        System.out.println("Wait for your turn");
        String line;
        try {
            line = in.readLine();
            int nullCounter = 0;

            while (true) {
                try {
                    while ((!line.equals("YOUR_TURN") && !line.equals("MOVE_OK") && !line.equals("END"))) {
                        line = in.readLine();
                    }
                    break;
                }catch (NullPointerException ignored){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    nullCounter++;
                    if(nullCounter > 15){
                        //if more than 15 nulls comes through buffer - connection with the opponent was lost
                        System.out.println("Opponent quit match or connection timed out");
                        disconnect();
                        connect();
                        gameInProgress = false;
                        return;
                    }
                }
            }

            switch (line) {
                case "YOUR_TURN":
                    printBoard(in.readLine());
                    break;
                case "MOVE_OK" :
                    System.out.println("MOVE OK");
                    printBoard(in.readLine());
                    waitForTurn();
                    break;
                case "END" :
                    printBoard(in.readLine());
                    System.out.println("game ended: " + receive());
                    gameInProgress = false;
                    break;
            }
        }
        catch (IOException e){
            e.printStackTrace();
            disconnect();
        }
    }

    private static void receiveList() {
        int size = Integer.parseInt(receive());
        System.out.println(size + " player(s) currently active:\n");
        for (int i = 0; i < size ; i++) {
            System.out.println(receive());
        }
        System.out.println("----");
    }

    private static boolean connect() {
        try {
            socket = new Socket(InetAddress.getByName(server_address), server_port);
            log("connected");
            return true;
        } catch (IOException e) {
            log("error");
            e.printStackTrace();
            return false;
        }
    }

    private static void log(String message){
        SimpleDateFormat sdf =  new SimpleDateFormat("DD-MM-YY HH:mm:ss");
        System.out.println("[" + sdf.format(Calendar.getInstance().getTime()) + "]C: " + message);
    }

    private static void disconnect(){
        out.println(LOGOUT);                 //send disconnect message
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void send(String choice){
        out.println(choice);
        log("sent " + choice + " to server");
    }

    private static String receive(){
        try {
            return in.readLine();
        } catch (IOException e) {
            disconnect();
            e.printStackTrace();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            log("connection was lost. client terminated");
        }
        return null;
    }

    private static void printBoard(String input){
        //parsing input
        input = input.substring(1,input.length()-1);
        String [] tmp = input.split(", ");
        int [] board = new int[9];
        for (int i = 0; i < tmp.length; i++) {
            board [i] = Integer.parseInt(tmp[i]);
        }

        //printing board
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            System.out.print('|');
            for (int j = 0; j < 3; j++) {
                switch (board[counter]) {
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

    private static int makeMove() {
        //this method returns a move given by user
        int move;
        do {
            System.out.println("please make a valid move");
            try{
                move = new Scanner(System.in).nextInt();
            }
            catch (InputMismatchException e){
                move = 0;
            }
        }   while (move < 1 || move > 9);
        return move;
    }
}
