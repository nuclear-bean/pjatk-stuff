#include <stdio.h>

void printEmptyLine();
void printFilledLine(char a,char b, char c);
void printHorizontalLine();
void printBoard();
int getPlayerNumber(char c);
void getMove();
int checkIfGameEnded();

char board [9] = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
char sign [2] = {'X','O'};
_Bool gameEnded;
int player = 1;

int main() {
    //print welcome message
    printf("\n\t\t\tTic Tac Toe\n\n  Player 1 (X)   /   Player 2 (O)\n\n");

    // print initial board
    printHorizontalLine();
    for (int i = 0; i < 7; i+=3) {
        printEmptyLine();
        printFilledLine((i+1) + '0',(i+2) + '0',(i+3) + '0');
        printEmptyLine();
        printHorizontalLine();
    }
    printf("\n");

    while (gameEnded == 0){

        int gameStatus = checkIfGameEnded();

        if(gameStatus == 0)
            getMove();
        printBoard();
    }
    printf("Restart to play again\n");

    return 0;
}

void printEmptyLine(){
    printf("     |     |     ");
    printf("\n");
}

void printFilledLine(char a,char b, char c){
    printf("  %c  |  %c  |  %c  ",a,b,c);
    printf("\n");
}

void printHorizontalLine(){
    printf("_________________");
    printf("\n");
}

void getMove(){
    //get desired field from player
    printf("\nPlayer %d make your move: ",player);
    int field;
    scanf("%d",&field);

    //validate move
    if(board[field-1] != ' ' || field > 9 || field < 1){
        printf("\nthis field is occupied!\n");
        return getMove(player);
    } else  //move is valid
        board[field - 1] = sign[player - 1];

    //check if game ended
    int result = checkIfGameEnded();

    if(result > 0){
        printf("Player %d won!\n",result);
        gameEnded = 1;
    } else if(result == -1){
        printf("draw!\n");
        gameEnded = 1;
    } else {
        if(player == 1)
            player=2;
        else
            player = 1;
    }
}

// checks if somebody won
// returns 1 if p1 won, 2 if p2 won, 0 in case game is still on, -1 in case of a draw
int checkIfGameEnded() {
    // check if somebody won
    //horizontal
    for (int i = 0; i < 8; i+=3) {
        if (board[i] == board[i + 1] && board[i + 1] == board[i + 2] && board[i] != ' ') {
            return getPlayerNumber(board[i]);
        }
    }
    //vertical
    for (int i = 0; i < 3; i++)
        if (board[i] == board[i+3] && board[i+3] == board[i+6] && board[i] != ' ')
            return getPlayerNumber(board[i]);

    //diagonal
    if (board[0] == board[4] && board[4] == board[8] && board[0] != ' ')
        return getPlayerNumber(board[0]);
    if (board[2] == board[4] && board[4] == board[6] && board[2] != ' ')
        return getPlayerNumber(board[2]);

    //check if draw
    int signCount = 0;
    for (int i = 0; i < 9; ++i) {
        if(board[i] == ' ')
            break;
        signCount++;
    }
    if(signCount == 9)
        return -1;

    // none of the above - game goes on
    return 0;
}

int getPlayerNumber(char c){
    if(c == 'X')
        return 1;
    if(c == 'O')
        return 2;
    return 0;
}

void printBoard(){
    printf("\n");
    printHorizontalLine();
    for (int i = 0; i <= 6; i+=3) {
        printEmptyLine();
        printFilledLine(board[i],board[i+1],board[i+2]);
        printEmptyLine();
        printHorizontalLine();
    }
    printf("\n");
}
