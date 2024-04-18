//package MySchachForu(m);
import java.util.Scanner;

public class Programm {
    String toMove = "white";
    String doneMove, move;
    String[]court = new String[64];

    void start(){
        CourtDefinition();
        EnterMove();
        ChangeTurn();
    }
    
    void CourtDefinition(){
        int ersatzI = 0;
        String[] pieces = new String[]{"R","N","B","Q","K","B","N","R"};
        for (int i = 0; i < court.length; i++) {
            court[i] = "0";
            if (i >= 8 && i <= 15) {
                court[i] = "bP";
            }
            if (i > 47 && i <= 55) {
               court[i] = "wP";
            }
            if (i <= 7) {
                court[i] = "b" + pieces[i];
            }
            if (i > 55) {
                court[i] = "w" + pieces[ersatzI];
                ersatzI++;
            }
        }
        System.out.println(court);

    }
    void EnterMove(){
        Scanner ScanObj = new Scanner(System.in);
        System.out.println("Make your move: ");
        move = ScanObj.nextLine();
        ScanObj.close();
        System.out.println(move);
    }
    void ChangeTurn(){
        if (toMove.equals("white") ) {
            toMove = "black";
        }else{
            toMove = "white";
        }

    }
    @SuppressWarnings("unused")
    void MakeMove(){
        String[] enteredMove = new String[10];
        enteredMove = move.split("");
        int fromRow = Integer.parseInt(enteredMove[0]);
        int fromColumn = Integer.parseInt(enteredMove[1]);
        int toRow = Integer.parseInt(enteredMove[2]);
        int toColumn = Integer.parseInt(enteredMove[3]);
        String chosenPiece = "";
    }
}
