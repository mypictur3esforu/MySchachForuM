//package MySchachForu(m);
import java.util.Scanner;

public class Programm {
    String toMove = "white";
    String doneMove, move;
    String[][] court = new String[8][8];
    String[] reihe2 = new String[8];
    String[] reihe3 = new String[8];
    String[] reihe4 = new String[8];
    String[] reihe5 = new String[8];
    String[] reihe6 = new String[8];
    String[] reihe7 = new String[8];
    String[] reihe8 = new String[]{"bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"};
    void start(){
        CourtDefinition();
        EnterMove();
        ChangeTurn();
    }
    
    void CourtDefinition(){
        for (int i = 0; i < reihe2.length; i++) {
          for(int z = 0; z < 8; z++){  
            court[i][z] = "0";
          }
            reihe4[i] = "0";
            reihe5[i] = "0";
            reihe6[i] = "0";
    
            reihe7[i] = "bP";
            reihe2[i] = "wP";
        }
    }
    void EnterMove(){
        Scanner ScanObj = new Scanner(System.in);
        System.out.println("Make your move: ");
        move = ScanObj.nextLine();
        ScanObj.close();
        System.out.println(move);
    }
    void ChangeTurn(){
        if (toMove == "white") {
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
