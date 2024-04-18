//package MySchachForu(m);
import java.util.Scanner;

public class Programm {
    String toMove = "white";
    String doneMove, move;
    String[]court = new String[64];
    int moveToSquare = 0;

    void start(){
        BordDefinition();
        EnterMove();
        ConvertSquare();
        CheckPiece();
        CheckMove();
        ChangeTurn();
    }
    
    void BordDefinition(){
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

    }
    void EnterMove(){
        Scanner ScanObj = new Scanner(System.in);
        System.out.println("Make your move: ");
        move = ScanObj.nextLine();
        ScanObj.close();
        System.out.println(move);
    }

    void ConvertSquare(){
        String[] squareChosen = new String[10]; 
        squareChosen = move.split("");
        //move[1] = Column
        //move[2] = Row
        for (int i = 0; i < Integer.parseInt(squareChosen[1]); i++) {
            moveToSquare += 8;
        }
        moveToSquare -= 1;
        switch (squareChosen[1]){
            case a -> moveToSquare += 1;
            case b -> moveToSquare += 2;
            case c -> moveToSquare += 3;
            case d -> moveToSquare += 4;
            case e -> moveToSquare += 5;
            case f -> moveToSquare += 6;
            case g -> moveToSquare += 7;
            case h -> moveToSquare += 8;

        }
        System.out.println();
    }

    void CheckPiece(){

    }

    void CheckMove(){

    }
    @SuppressWarnings("unused")
    void MakeMove(){
        String[] enteredMove = new String[10];
        enteredMove = move.split("");
    }

    void ChangeTurn(){
        if (toMove.equals("white") ) {
            toMove = "black";
        }else{
            toMove = "white";
        }

    }
}
