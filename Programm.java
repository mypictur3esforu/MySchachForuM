//package MySchachForu(m);
import java.util.Scanner;

public class Programm {
    String toMove = "white";
    String doneMove, move;
    String[]court = new String[64];
    int squareNumber = 0;

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
        squareNumber = 63;
        for (int i = 0; i < Integer.parseInt(squareChosen[2]); i++) {
            squareNumber -= 8;
        }
        squareNumber += LetterConverter(squareChosen[1]);
        System.out.println(squareNumber);
    }
    int LetterConverter(String letter){
        int resolvingNumber = 0;
        switch (letter){
            case "a" -> resolvingNumber = 1;
            case "b" -> resolvingNumber = 2;
            case "c" -> resolvingNumber = 3;
            case "d" -> resolvingNumber = 4;
            case "e" -> resolvingNumber = 5;
            case "f" -> resolvingNumber = 6;
            case "g" -> resolvingNumber = 7;
            case "h" -> resolvingNumber = 8;
        }
        return resolvingNumber;
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
