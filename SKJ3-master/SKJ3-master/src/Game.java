import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Game {
    static List<Player> matchMaking = new ArrayList<>();
    private Player player1;
    private Player player2;
    private List<Player> players = new ArrayList<>();
    private int [] board = new int[9];
    private int winner;

    Game() throws IOException {

        //take two first client
        player1 = matchMaking.get(0);
        player2 = matchMaking.get(1);

        //remove them from matchMaking list
        matchMaking.remove(0);
        matchMaking.remove(0);

        //choosing who will start the game
        if(Math.random() > 0.5){
            player1.send("FIRST");
            player2.send("SECOND");
        }
        else {
            player2.send("FIRST");
            player1.send("SECOND");
            //switch players
            Player tmp = player2;
            player2 = player1;
            player1 = tmp;
        }

        //add them to players list
        players.add(player1);
        players.add(player2);

        players.forEach(player -> player.send(Arrays.toString(board)));                //sending initial board state

        while (!gameCompleted()) {
            try {
                makeMove(1, Integer.parseInt(player1.in.readLine()));
                Broadcaster.broadcast(this);
                if (gameCompleted()) {
                    free(player1.getHandler());
                    free(player2.getHandler());
                    break;
                }
                makeMove(2, Integer.parseInt(player2.in.readLine()));
                Broadcaster.broadcast(this);
                if (gameCompleted()) {
                    free(player1.getHandler());
                    free(player2.getHandler());
                    break;
                }
            }
            catch (NumberFormatException e){
                //this is executed if one of the players disconnected during the game
                player1.send("END");        //both of the player's are prompted that the game is over
                player2.send("END");
                players.forEach(player -> player.send(Arrays.toString(board)));
                System.out.println("server ending game on force quit");
                return;
            }
        }

        System.out.println("game between player " + player1.getId() + " and player " + player2.getId() + " ended");
    }

    private void makeMove(int playerNr, int field){
        //this method sets the area accordingly with user input
        //and sends current board to user

        Player currentPlayer;
        Player opponent;

        if(playerNr == 1){
            currentPlayer = player1;
            opponent      = player2;
        }
        else {
            currentPlayer = player2;
            opponent      = player1;
        }

        board[field-1] = playerNr;

        if(gameCompleted()) {
            players.forEach(player -> player.send("END"));
            players.forEach(player -> player.send(Arrays.toString(board)));
            if(winner == 1){
                player1.send("YOU WON");
                player2.send("YOU LOST");
            }
            else if(winner == 2){
                player1.send("YOU LOST");
                player2.send("YOU WON");
            }
            else
                players.forEach(player -> player.send("DRAW"));

            //close game
            free(player1.getHandler());
            free(player2.getHandler());
            Broadcaster.broadcast(this);
            return;
        }

        //here checking if move is valid can be made
        currentPlayer.send("MOVE_OK");
        opponent.send("YOUR_TURN");

        //sending board to players
        players.forEach(player -> player.send(Arrays.toString(board)));
    }

    private synchronized void free(Runnable t1) {
        synchronized (t1){
            t1.notify();
        }
    }

    private boolean gameCompleted(){
        return boardFull() || someoneWon();
    }

    private boolean someoneWon() {
        //check horizontal
        for (int i = 0; i < 3 ; i++) {
            int firstInRow = i*3;
            if(board[firstInRow] == board[firstInRow+1] && board[firstInRow+1] == board[firstInRow+2]) {
                if(board[firstInRow] == 0)
                    continue;
                winner = board[firstInRow];
                return true;
            }
        }

        //check vertical
        for (int i = 0; i < 3 ; i++) {
            if(board[i] == board[i+3] && board[i+3] == board[i+6]) {
                if(board[i] == 0)
                    continue;
                winner = board[i];
                return true;
            }
        }

        //check diagonal
        if(board[0] == board[4] && board[4] == board[8] && board[4] != 0) {
            winner = board[0];
            return true;
        }
        else if (board[2] == board[4] && board[4] == board[6] && board[4] != 0) {
            winner = board[0];
            return true;
        }

        return false;
    }

    private boolean boardFull() {
        for (int i : board) {
            if(i == 0)
                return false;
        }
        winner = 0;
        return true;
    }

    Player getPlayer1() {
        return player1;
    }

    Player getPlayer2() {
        return player2;
    }

    int[] getBoard() {
        return board;
    }

}


//TODO WYSLAC STAN GRY PO WYGRANEJ