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
    boolean reverse = false;

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
        start();
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
    }

    String Regex(String stringTosearch, String expression, int arrayNumber){
        Pattern findLetter = Pattern.compile(expression, Pattern.MULTILINE);
        String[] splitted = new String[10];
        splitted = stringTosearch.split(findLetter.pattern());
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
        chosenPiece = toMove + " ";
        switch (convertToPiece){
            case " ", "P" -> chosenPiece += "Pawn";
            case "R" -> chosenPiece += "Rook";
            case "N" -> chosenPiece += "Knight";
            case "B" -> chosenPiece += "Bishop";
            case "Q" -> chosenPiece += "Queen";
            case "K" -> chosenPiece += "King";
        }
        System.out.println("Piece chosen: " + chosenPiece);
        return chosenPiece;
    }
    void CheckMove(){
        boolean moveLegal = false;
        System.out.println("chosen: " + chosenPiece);
        String piecesWOColor = Regex(chosenPiece, "[a-z]{5}.", 1);
        System.out.println("piecesWOColor: " + piecesWOColor);
        switch (piecesWOColor){
            case "Rook" -> moveLegal = CheckRook();
            case "Knight" -> CheckKnight();
            case "Bishop" -> CheckBishop();
            case "Queen" -> CheckQueen();
            case "King" -> CheckKing();
            case "Pawn" -> CheckPawn();
        }
    }

    boolean CheckRook(){
        if (CheckColumn(move)) {
            System.out.println("ROOOOKKKK MOVES!!!");
            return true;
        } else if (CheckRow(move)) {
            System.out.println("Wrong direction");
            return true;
        }
        return false;
    }

    boolean CheckKnight(){
        return false;
    }

    boolean CheckBishop(){
        if(CheckDiagonal(isOnSquare, moveToSquare)){
            System.out.println("Bishop moves");
            return true;
        }
        return false;
    }

    boolean CheckQueen(){
        if (CheckRow(move) || CheckColumn(move)) {
            System.out.println("Queen moves");
            return true;
        }
        return false;
    }

    boolean CheckKing(){
        CheckSurround(isOnSquare, moveToSquare);
        System.out.println("King moves");
        return false;
    }

    boolean CheckPawn(){
        if (toMove == "white") {
            if (isOnSquare >= 48 && isOnSquare <= 55) {
                if (CheckInFront(isOnSquare, moveToSquare, -1) || CheckInFront(isOnSquare - 1, moveToSquare, -1)) {
                    return true;
                }
            }else{
                if (CheckInFront(isOnSquare, moveToSquare, -1)) {
                    return true;
                }
            }
        }
        if (toMove == "black") {
            if (isOnSquare >= 8 && isOnSquare <= 15) {
                if (CheckInFront(isOnSquare, moveToSquare, 1) || CheckInFront(isOnSquare +1, moveToSquare, 1)) {
                    return true;
                }
            }else{
                if (CheckInFront(isOnSquare, moveToSquare, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean CheckInFront(int toCheckStart, int toCheckEnd, int frontOrBack){
        if (toCheckStart + frontOrBack == toCheckEnd) {
            return true;
        }
        return false;
    }

    boolean CheckSurround(int toCheckStart, int toCheckEnd){
        if (toCheckStart + 1 == toCheckEnd || toCheckStart - 1 == toCheckEnd) {
            return true;
        }
        for (int i = 7; i < 10; i++) {
            if (toCheckStart + i == toCheckEnd || toCheckStart - i == toCheckEnd) {
                return true;
            }
        }
        return false;
    }

    boolean CheckColumn(String toCheck){
        String wCapLetter = Regex(toCheck, "[A-Z]", 1);
        //funktioniert!! wCapLetter = Regex(wCapLetter, "[0-9]", 1);
        return Regex(wCapLetter, "[0-9]", 1).equals(Regex(wCapLetter, "[0-9]", 0));
    }

    boolean CheckRow(String toCheck){
        String wCapLetter = Regex(toCheck, "[A-Z]", 1);
        //funktioniert!! wCapLetter = Regex(wCapLetter, "[0-9]", 1);
        return Regex(wCapLetter, "[a-z]", 1).equals(Regex(wCapLetter, "[a-z]", 2));
    }

    boolean CheckDiagonal(int toCheckStart, int toCheckEnd){
        int checkLeft = 7;
        int checkRight = 9;
        for (int i = 0; i <= 8; i++) {
            if (toCheckStart + checkLeft == toCheckEnd || toCheckStart + checkRight == toCheckEnd || toCheckStart - checkLeft == toCheckEnd || toCheckStart - checkRight == toCheckEnd) {
                return true;
            }
            checkLeft += 7;
            checkRight += 9;
        }
        return false;
    }

    void ChangeTurn(){
        if (toMove.equals("white")) {
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
