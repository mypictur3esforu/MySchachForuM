//package MySchachForu(m);
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Programm {
    String toMove = "white";
    String doneMove, move, chosenPiece;
    String errorType = "";
    String[] court = new String[64];
    int squareNumber = 0;
    int isOnSquare = 0;
    int moveToSquare = 0;
    boolean gameRunning = false;
    boolean moveLegal = false;

    void start(){
        if (!gameRunning) {
        //BordDefinition();
        TestBoardDefinition();
        gameRunning = true;
        }
        EnterMove();
        ConvertNotationToPiece(move);
        FromToSquare();
        CheckInput();
        CheckMove();
        TakePossible(moveToSquare);
        if (moveLegal) {
         MakeMove();
         ChangeTurn();
        }else{
            MoveIllegal();
        }
        start();
    }

    void MoveIllegal(){
        System.out.println("Der Zug " + move + " ist leider nicht legal. " + errorType + "\nDu musst einen neuen Zug eingeben!");
        errorType = "";
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

    void TestBoardDefinition(){
        for (int i = 0; i < court.length; i++) {
            court[i] = "0";
        }
        court[59] = "D";
        court[56] = "R";
    }

    void EnterMove(){
        Scanner ScanObj = new Scanner(System.in);
        System.out.println("Make your move: ");
        move = ScanObj.nextLine();
        //ScanObj.close();
        System.out.println(move);
    }

    void CheckInput(){
        String[] pieceOnSquare = new String[2];
        pieceOnSquare = court[isOnSquare].split("");
        if (ConvertColor(pieceOnSquare[0]).equals(toMove)) {
            moveLegal = pieceOnSquare[1].equals(Regex(move, "[a-z]...", 0));
            if (!moveLegal) {
                ErrorType("Auf dem Feld steht die gewählte Figur nicht!");
            }
        }else{
            ErrorType("Die gewählte Figur gehört nicht zu dir!");
            moveLegal = false;
        }
    }

    void FromToSquare(){
        String fromSquare, toSquare;
        fromSquare = Regex(move, "[a-z][0-9]$", 0);
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

    String ConvertColor(String colorShortcut){
        switch (colorShortcut){
            case "w" ->{ return "white";}
            case "b" -> {return "black";}
        }
        return "ERROR";
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
        System.out.println("chosen: " + chosenPiece);
        String piecesWOColor = Regex(chosenPiece, "[a-z]{5}.", 1);
        System.out.println("piecesWOColor: " + piecesWOColor);
        switch (piecesWOColor){
            case "Rook" -> moveLegal = CheckRook();
            case "Knight" -> moveLegal = CheckKnight();
            case "Bishop" -> moveLegal = CheckBishop();
            case "Queen" -> moveLegal = CheckQueen();
            case "King" -> moveLegal = CheckKing();
            case "Pawn" -> moveLegal = CheckPawn();
        }
        if (!moveLegal) {
                ErrorType("Diese Figur kann nicht auf das gewünschte Feld gehen!");
        }
    }

    boolean CheckRook(){
        if (CheckColumn(move)) {
            if (CheckBetweenColumnRow(isOnSquare, moveToSquare, "column")) {
                System.out.println("ROOOOKKKK MOVES!!!");
                return true;
            }
        } else if (CheckRow(move)) {
            if (CheckBetweenColumnRow(isOnSquare, moveToSquare, "row")) {
                System.out.println("Wrong direction");
                return true;
            }
        }
        return false;
    }

    boolean CheckKnight(){
        if (CheckLMovement(isOnSquare, moveToSquare)) {
            System.out.println("Knight moves");
            return true;
        }
        return false;
    }

    boolean CheckBishop(){
        if(CheckDiagonal(isOnSquare, moveToSquare) && CheckBetweenDiagonal(isOnSquare, moveToSquare)){
                System.out.println("Bishop moves");
                return true;
        }
        return false;
    }

    boolean CheckQueen(){
        if (CheckRow(move) || CheckColumn(move)) {
            if (CheckBetweenColumnRow(isOnSquare, moveToSquare, "row") || CheckBetweenColumnRow(isOnSquare, moveToSquare, "column")) {
                System.out.println("Queen moves");
                return true;
            }
        }
        return CheckDiagonal(isOnSquare, moveToSquare) && CheckBetweenDiagonal(isOnSquare, moveToSquare);
    }

    boolean CheckKing(){
        if (CheckSurround(isOnSquare, moveToSquare)) {
            System.out.println("King moves");
            return true;
        }
        return false;
    }

    boolean CheckPawn(){
        if (CheckBetweenColumnRow(isOnSquare, moveToSquare, "column")) {
            if (toMove.equals("white")) {
                if (isOnSquare >= 48 && isOnSquare <= 55) {
                    if (CheckInFront(isOnSquare, moveToSquare, -2)) {
                        System.out.println("Pawn moves! 2");
                        return true;
                    } else {
                        return CheckInFront(isOnSquare, moveToSquare, -1);
                    }
                } else {
                    return CheckInFront(isOnSquare, moveToSquare, -1);
                }
            }
            if (toMove.equals("black")) {
                if (isOnSquare >= 8 && isOnSquare <= 15) {
                    if (CheckInFront(isOnSquare, moveToSquare, 2)) {
                        System.out.println("Pawn moves! 2");
                        return true;
                    }
                }
                    return CheckInFront(isOnSquare, moveToSquare, 1);

            }
        }
        return false;
    }

    boolean CheckInFront(int toCheckStart, int toCheckEnd, int frontOrBack){
        frontOrBack *= 8;
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

    boolean CheckLMovement(int toCheckStart, int toCheckEnd){
        int[] possibleMoves = new int[]{10, 15, 6, 17};
        for (int i = 0; i < possibleMoves.length; i++) {
            if (toCheckStart + possibleMoves[i] == toCheckEnd || toCheckStart - possibleMoves[i] == toCheckEnd) {
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

    boolean CheckBetweenColumnRow(int toCheckStart, int toCheckEnd, String columRow){
        int add = 0;
        int reverse = 1;
        switch (columRow){
            case "row" -> add = 1;
            case "column" -> add = 8;
        }
        if (toCheckStart > toCheckEnd) {
            reverse = -1;
        }
            for (int i = add; i <= 64; i += add) {
                if (toCheckStart + (i * reverse) == toCheckEnd) {
                    return true;
                }
                    if (!court[toCheckStart + (i * reverse)].equals("0")) {
                        ErrorType("Die Figur kann dort nicht hin, weil eine andere dazwischen steht!");
                        moveLegal = false;
                        return false;

                }
        }
        return true;
        //return TakePossible(toCheckEnd);
    }

    boolean CheckBetweenDiagonal(int toCheckStart, int toCheckEnd){
        int y = toCheckEnd;
        int x = toCheckStart;
        int add;
        int reverse = 1;
        if (toCheckStart > toCheckEnd) {
            y = toCheckStart;
            x = toCheckEnd;
            reverse = -1;
        }
        if ((y - x) % 7 == 0) {
            add = 7;
        }else if ((toCheckEnd - toCheckStart) % 9 == 0) {
            add = 9;
        }else{
            ErrorType("Das gewünschte Feld ist nicht diagonal zum dem, auf dem die Figur steht!");
            return false;
        }
        add *= reverse;
        return CheckOneDiagonal(toCheckStart, toCheckEnd, add);
    }

    boolean CheckOneDiagonal(int toCheckStart, int toCheckEnd, int i){
        for (i = i; i < 100; i += i) {
            if (toCheckStart + i == toCheckEnd) {
                return true;
            }
            if (!(court[toCheckStart + i].equals("0"))) {
                ErrorType("Die Figur kann dort nicht hin, da ein andere Figur dazwischen steht!");
                return false;
            }
        }
        return false;
    }

    boolean TakePossible(int toCheckEnd){
        String[] endSplit = new String[3];
        endSplit = court[toCheckEnd].split("");
        if (Objects.equals(ConvertColor(endSplit[0]), toMove)) {
            moveLegal = false;
            ErrorType("You can not take your own pieces!");
            return false;
        }
        return true;
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
        // for (int i = 0; i < court.length; i++)
        for (String s : court) {
            System.out.println(s);
        }
    }

    void ErrorType(String error){
        if (errorType.isEmpty()) {
            errorType = error;
        }
    }
}
