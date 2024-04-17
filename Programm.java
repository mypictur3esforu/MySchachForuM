//package MySchachForu(m);

import java.net.Inet4Address;
import java.util.Scanner;

import javax.print.DocFlavor.STRING;

public class Programm {
    String toMove = "white";
    String doneMove, move;
    String[][] court = new String[8][8];
    String[] reihe1 = new String[]{"wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"};
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
        for (int i = 0; i < reihe1.length; i++) {
            reihe3[i] = "0";
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
    void MakeMove(){
        String[] enteredMove = new String[10];
        enteredMove = move.split("");
        int fromRow = Integer.parseInt(enteredMove[0]);
        int fromColumn = Integer.parseInt(enteredMove[1]);
        int toRow = Integer.parseInt(enteredMove[2]);
        int toColumn = Integer.parseInt(enteredMove[3]);
        String chosenPiece = 
    }
}
