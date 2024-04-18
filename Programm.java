//package MySchachForu(m);
import java.util.Scanner;
import java.util.regex.Pattern;

public class Programm {
    String toMove = "white";
    String doneMove, move;
    String[] court = new String[64];
    String chosenPiece;
    int squareNumber = 0;
    int isOnSquare = 0;
    int moveToSquare = 0;
    boolean gameRunning = false;

    void start(){
        if (!gameRunning) {
        BordDefinition();
        gameRunning = true;
        }
        EnterMove();
        ConvertNotationToPiece(move);
        FromToSquare();
        MakeMove();
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
        //ScanObj.close();
        System.out.println(move);
    }

    void FromToSquare(){
        String fromSquare = new String();
        fromSquare = Regex(move, "[a-z][0-9]$", 0);
        String toSquare = new String();
        toSquare = Regex(move, "[A-Z]..", 1);
        isOnSquare = ConvertSquare(fromSquare);
        moveToSquare = ConvertSquare(" " + toSquare);

        System.out.println("fromSquare: " + fromSquare);
        System.out.println("toSquare: " + toSquare);
        System.out.println("isOnSquare: " + isOnSquare);
        System.out.println("moveToSquare: " + moveToSquare);
    }

    void MakeMove(){
        court[moveToSquare] = court[isOnSquare];
        court[isOnSquare] = "0";
        //CheckBoard();
        start();
    }

    String Regex(String stringTosearch, String expression, int arrayNumber){
        Pattern findLetter = Pattern.compile(expression, Pattern.MULTILINE);
        String[] splitted = new String[10];
        splitted = move.split(findLetter.pattern());
        //System.out.println("splitted = " + splitted[arrayNumber]);
        return splitted[arrayNumber];
    }

//Methode erhält ein Feld als String (z.B. h6) und konvertiert es dann in die passende Zahl für Array court[]
    int ConvertSquare(String searchedSquare){
        String[] squareChosen = new String[3];
        squareChosen = searchedSquare.split("");
        squareNumber = 63;
        for (int i = 0; i < Integer.parseInt(squareChosen[2]); i++) {
            squareNumber -= 8;
        }
        squareNumber += LetterConverter(squareChosen[1]);
        System.out.println("squareNumber: " + squareNumber);
        return squareNumber;
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


    String ConvertNotationToPiece(String notation){
        String convertToPiece = Regex(notation, "[a-z]",0);
        if (toMove == "white") {
            chosenPiece = "white ";
        }
        if (toMove == "black") {
            chosenPiece = "black ";
        }
        switch (convertToPiece){
            case " ", "P" -> chosenPiece += "Pawn";
            case "R" -> chosenPiece += "Rook";
            case "N" -> chosenPiece += "Knight";
            case "B" -> chosenPiece += "Bisshop";
            case "Q" -> chosenPiece += "Queen";
            case "K" -> chosenPiece += "King";
        }
        System.out.println("Piece chosen: " + chosenPiece);
        return chosenPiece;
    }
    void CheckMove(){

    }


    void ChangeTurn(){
        if (toMove.equals("white") ) {
            toMove = "black";
        }else{
            toMove = "white";
        }

    }

    void CheckBoard(){
        for (int i = 0; i < court.length; i++) {
            System.out.println(court[i]);
        }
    }
}
