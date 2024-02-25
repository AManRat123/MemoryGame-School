import java.util.Scanner;
public class Game {
    int p1Score = 0;
    int p2Score = 0;

    boolean restart = true;

    boolean p1Turn = true;

    Scanner sc = new Scanner(System.in);
    Board board;

    public void start(){
        System.out.print("Enter an even board column/row number: ");
        int size = sc.nextInt();
        sc.nextLine();
        board = new Board(size);

        while(restart){
            while (!board.Over()) {
                move();
            }
            board.printBoard(p1Score,p2Score);

            if (p1Score > p2Score) {
                System.out.println("Player 1 Wins!");
            } else if (p2Score > p1Score) {
                System.out.println("Player 2 Wins!");
            } else {
                System.out.println("It's a draw!");
            }

            System.out.print("Restart? [y/n]: ");
            if (sc.nextLine().toLowerCase().charAt(0) == 'y') {
                restart();
            }
            else{
                restart = false;
                System.out.println("Thank you for playing.");
            }
        }
    }

    public void restart(){
        p1Score = 0;
        p2Score = 0;

        p1Turn = true;

        board.shuffle();


        for(Cards[] c : board.board){
            for(Cards i : c){
                i.taken = false;
                i.shown = false;
            }
        }
    }


    public boolean withinRange(int min, int max, int value){
        return value>=min && value<=max;
    }

    public void move(){
        board.printBoard(p1Score,p2Score);
        String player;
        if (p1Turn) {
            player = "Player 1";
        }
        else {
            player = "Player 2";
        }

        System.out.println(player + "'s turn now!");
        System.out.print("What column is your first guess?: ");
        int column1 = sc.nextInt();
        sc.nextLine();

        System.out.print("What row is your first guess?: ");
        int row1 = sc.nextInt();
        sc.nextLine();

        System.out.println();

        System.out.print("What column is your second guess?: ");
        int column2 = sc.nextInt();
        sc.nextLine();
        System.out.print("What row is your second guess?: ");
        int row2 = sc.nextInt();
        sc.nextLine();

        column2--;
        column1--;
        row2--;
        row1--;


        while(!((column1!=column2 || row1!=row2) &&  withinRange(0,board.board.length-1,column1) && withinRange(0,board.board.length-1,row1) && withinRange(0,board.board.length-1,row2) && withinRange(0,board.board.length-1,column2) &&!board.getValue(column1,row1).taken &&!board.getValue(column2,row2).taken)){
            if(!(withinRange(0,board.board.length-1,column1) && withinRange(0,board.board.length-1,row1) && withinRange(0,board.board.length-1,row2) && withinRange(0,board.board.length-1,column2))) {
                System.out.println("That is out of range, try again");
            }
            else if(!(!board.getValue(column1,row1).taken &&!board.getValue(column2,row2).taken)){
                System.out.println("That's already taken, try that again");
            }
            else if(column1==column2 && row1==row2){
                System.out.println("You can't select the same card, try that again");
            }
            System.out.print("What column is your first guess?: ");
            column1 = sc.nextInt();
            sc.nextLine();
//        System.out.println();
            System.out.print("What row is your first guess?: ");
            row1 = sc.nextInt();
            sc.nextLine();

            System.out.println();

            System.out.print("What column is your second guess?: ");
            column2 = sc.nextInt();
            sc.nextLine();
            System.out.print("What row is your second guess?: ");
            row2 = sc.nextInt();
            sc.nextLine();

            column2--;
            column1--;
            row2--;
            row1--;
        }

        board.setShown(true, column1,row1);
        board.setShown(true, column2,row2);
        board.printBoard(p1Score,p2Score);
        Cards card1 = board.getValue(column1,row1);
        Cards card2 = board.getValue(column2,row2);

        if(card1.getValue() == card2.getValue()){
            card1.taken = true;
            card2.taken = true;

            System.out.println("Match!");

            if(p1Turn){
                p1Score+=2;
            }
            else{
                p2Score+=2;
            }
        }
        else {
            System.out.println("Wrong");
        }

        card1.shown = false;
        card2.shown = false;

        p1Turn = !p1Turn;
    }
}