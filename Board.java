import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
public class Board {
    Cards[][] board;

    public void shuffle(){
        int n = board.length;
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                Cards temp = board[i][j];
                int colRand = new Random().nextInt(n - i) + i;
                int rowRand = new Random().nextInt(n - j) + j;
                board[i][j] = board[colRand][rowRand];
                board[colRand][rowRand] = temp;
            }
        }
    }

    public Board(int size){
        board = new Cards[size][size];

        int counter = 1;
        boolean repeat = true;

        for(int row=0; row< board.length; row++){
            for(int column = 0; column<board[row].length; column++){
                board[row][column] = new Cards(counter);
                if(!repeat) {
                    counter++;
                    repeat = true;
                }
                else if(repeat){
                    repeat = false;
                }
            }
        }
        shuffle();
    }

    public void setShown(boolean shownOrNot, int column, int row){
        board[row][column].shown = shownOrNot;
    }

    public Cards getValue(int column, int row){
        return board[row][column];
    }

    public boolean Over(){
        boolean Over = true;
        for(Cards[] c : board){
            for(Cards i : c){
                if(!i.taken){
                    Over = false;
                }
            }
        }
        return Over;
    }

    public void printBoard(int p1Score, int p2Score) {
        int rowLength = board.length;
        int colLength = board[0].length;
        int maxCardValueLength = String.valueOf(rowLength * colLength).length();

        System.out.println("Player 1 Score: "+p1Score);
        System.out.println("Player 2 Score: "+p2Score);

        System.out.print("      ");
        for (int col = 0; col < colLength; col++) {
            System.out.printf("[ %-" + maxCardValueLength + "s] ", col + 1);
        }
        System.out.println();

        for (int i = 0; i < rowLength; i++) {
            System.out.printf("[ %-" + maxCardValueLength + "s] ", i + 1);

            for (int j = 0; j < colLength; j++) {
                Cards card = board[i][j];
                if (card.shown) {
                    int value = card.getValue();
                    String cardValue = String.valueOf(value);
                    String padding = " ".repeat(maxCardValueLength - cardValue.length());

                    System.out.printf("[%s%s] ", padding, cardValue);
                }
                else if(card.taken){
                    System.out.print("[ ✅ ] ");
                }
                else {
                    System.out.print("[ X ] ");
                }
            }
            System.out.println();
        }
    }
}
