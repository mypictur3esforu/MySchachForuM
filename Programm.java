//package MySchachForu(m);
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Programm {
    String toMove = "white";
    String move, chosenPiece, moveCombo, checkFrom;
    String enPassant = "false";
    String errorType = "";
    String winner = "";
    String[] court = new String[64];
    String[] isInCheckArray = new String[]{"", ""};
    int enPassantSquare;
    int squareNumber = 0;
    int isOnSquare = 0;
    int moveToSquare = 0;
    int premoveOrder = 0;
    int step = 0;
    int blackCheckCount = 0;
    int whiteCheckCount = 0;
    boolean gameRunning = false;
    boolean moveLegal = false;
    boolean premoveActiv = false;
    boolean whiteKingMoved = false;
    boolean blackKingMoved = false;
    boolean gameContinue = true;
    boolean isInCheck = false;

    void start(){
        if (gameRunning) {
            CheckGameState();
        }
        if (!gameRunning && gameContinue) {
        BordDefinition();
        //TestBoardDefinition();
        gameRunning = true;
        }
        if (gameRunning) {
        if (!premoveActiv) {
            EnterMove();
        }else{Premove();}
        ConvertNotationToPiece(move);
        FromToSquare();
        CheckInput();
        if (moveLegal) {
            CheckMove();
        }
        TakePossible(moveToSquare);
        if (moveLegal) {
         MakeMove();
         ChangeTurn();
        }else{
            MoveIllegal();
        }
        EnPassantable();
            start();
        }
    }
    void GameOver(){
        gameRunning = false;
        gameContinue = false;
        System.out.println(winner + " gewinnt!");
    }

    void CheckGameState(){
        if (GetKingPosition("white") == -1) {
            winner = "Schwarz";
        }
        if (GetKingPosition("black") == -1) {
            winner = "Weiß";
        }
        CheckForCheck("black");

        if (whiteCheckCount > 1) winner = "Schwarz";
        if (blackCheckCount > 1) winner = "Weiß";

        if (!winner.isEmpty()) {
            GameOver();
        }
    }

    int GetKingPosition(String color){
        String[] kings = new String[2];
        for (int i = 0; i < court.length; i++) {
            if (ConvertSquareToPiece(court[i], false).equals("King") && ConvertPieceColor(i).equals(color)) {
                return i;
            }
        }
        return -1;
    }
    //methode hat parameter, damit man nicht nur Position von Königen testen kann, bei CheckGameSate ist die Funktion standardmäßig auf Schwarz
    void CheckForCheck(String toCheck){
        if (toCheck.equals("white") || toCheck.equals("black")) {
            toCheck = GetKingPosition(toCheck) +"";
        }
        int kingToCheck = Integer.parseInt(toCheck);
        for (int i = 0; i < 1; i++) {
            if (CheckDiagonalCheck(kingToCheck) || CheckLMovementCheck(kingToCheck) ||  CheckForColumnRowCheck(kingToCheck)) {
                isInCheck = true;
                isInCheckArray[step] = ConvertPieceColor(kingToCheck);
                CheckIfNewCheck();
                CheckForMate();
                break;
            }
            isInCheck = false;
        }
        step++;
        toCheck = ConvertPieceColor(kingToCheck);
        if (toCheck.equals("black")) {
            CheckForCheck("white");
        }
        step = 0;
    }

    void CheckIfNewCheck(){
        if (isInCheckArray[0].equals("black")) blackCheckCount++;
        if (isInCheckArray[1].equals("white")) whiteCheckCount++;

    }

    void CheckForMate(){

    }

    void EnPassantable(){
        enPassantSquare = 1000;
        if (enPassant.equals("true")) switch (toMove) {
            case "white" -> enPassantSquare = moveToSquare - 8;
            case "black" -> enPassantSquare = moveToSquare + 8;
            //bisschen verdreht weil es ist immer der andere am Zug
        }
        enPassant = "false";
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
        Arrays.fill(court, "0");
        court[56] = "wR";
        court[4] = "bK";
        court[60] = "wK";
        court[42] = "bR";
    }

    void EnterMove(){
        Scanner ScanObj = new Scanner(System.in);
        System.out.println("Make your move: ");
        move = ScanObj.nextLine();
        //ScanObj.close();
        System.out.println(move);
        if (move.equals("premove")) {
            premoveOrder = 0;
            Scanner ScanObjNr2 = new Scanner(System.in);
            System.out.println("Type in your move order: ");
            moveCombo = ScanObjNr2.nextLine();
            Premove();
        }
    }

    void Premove(){
        premoveActiv = true;
        String[] moves = moveCombo.split(" ");
        move = moves[premoveOrder];
        moves[premoveOrder] = "Done";
        if (moves[moves.length - 1].equals("Done")) {
            premoveActiv = false;
        }
        premoveOrder++;
    }

    void CheckInput(){
        String[] pieceOnSquare = new String[2];
        pieceOnSquare = court[isOnSquare].split("");
        if (ConvertColor(pieceOnSquare[0]).equals(toMove)) {
            moveLegal = pieceOnSquare[1].equals(Regex(move, "[a-z]...", 0));
            if (!moveLegal) {
                ErrorType("Auf dem Feld steht die gewählte Figur nicht!");
            }
        }else if (court[isOnSquare].equals("0")) {
            ErrorType("Auf disem Feld steht keine Figur!");
            moveLegal = false;
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

    String ConvertSquareBack(int chosenSquare){
        String square;
        square = NumberToLetterConverter(chosenSquare % 8);
        square += (court.length - (chosenSquare - (chosenSquare % 8)))/ 8;
        return square;
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

    String NumberToLetterConverter(int number){
        String[] letter = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
        return letter[number];
    }

    String ConvertColor(String colorShortcut){
        switch (colorShortcut){
            case "w" ->{ return "white";}
            case "b" -> {return "black";}
        }
        return "ERROR";
    }

    String ConvertNotationToPiece(String notation){
        //man kann alles unnötige wegmachen, sodass man immer nen Array mit 2 teilen hat und braucht dementsprechen keine extra " "
        String pieceToConvert = Regex(notation, "[a-z]",0);
        chosenPiece = toMove + " ";
        chosenPiece += ConvertToPiece(pieceToConvert);
        System.out.println("Piece chosen: " + chosenPiece);
        return chosenPiece;
    }

    String ConvertSquareToPiece(String square, boolean color){
        //square z.B. court[5]
        if (square.equals("0")) return "0";
        String[] squareOccupation = square.split("");
        if (color) {
            return ConvertColor(squareOccupation[0]) + " " + ConvertToPiece(squareOccupation[1]);
        }
        return ConvertToPiece(squareOccupation[1]);
    }

    String ConvertToPiece(String toConvert){
        //System.out.println("Convert: " + toConvert);
        String piece = "ERROR!!!";
        switch (toConvert){
            case " ", "P" -> piece = "Pawn";
            case "R" -> piece = "Rook";
            case "N" -> piece = "Knight";
            case "B" -> piece = "Bishop";
            case "Q" -> piece = "Queen";
            case "K" -> piece = "King";
        }
        return piece;
    }

    String ConvertPieceColor(int square){
        //square = Feld Nummer Feld a8 wäre also 0
        String[] pieceOnSquare = court[square].split("");
        return ConvertColor(pieceOnSquare[0]);
    }

    int GetRow(int toCheck){
        return (toCheck - (toCheck % 8)) / 8;
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
        if (CheckCastle()) {
            System.out.println("Castling");
            return true;
        }
        return false;
    }

    boolean CheckCastle(){
        switch (toMove){
            case "black" -> step = 0;
            case "white" -> step = 1;
        }
        if (Math.abs(isOnSquare - moveToSquare) != 2) {
            ErrorType("Zu diesem Feld kann der König nicht rochieren!");
            return false;
        }
        if (step == 0 && blackKingMoved) {
            ErrorType("Der schwarze König wurde bereits bewegt und darf nicht mehr rochieren!");
            return false;
        }
        if (step == 1 && whiteKingMoved) {
            ErrorType("Der weiße König wurde bereits bewegt und darf nicht mehr rochieren!");
            return false;
        }
        if (isInCheckArray[step].isEmpty()) {
            if (CheckBetweenKingRook()) {
                return true;
            }else{
                ErrorType("Der König befindet sich im Schach und somit nicht rochieren!");
                return false;
            }
        }
        return false;
    }
    
    boolean CheckBetweenKingRook(){
        int maximum = moveToSquare - isOnSquare;
        int reverse = 1;
        if (isOnSquare > moveToSquare) {
            reverse = -1;
            maximum = isOnSquare - moveToSquare;
        }
        int hallo = 0;
        for (int i = 1; i <= maximum; i++) {
            hallo++;
            System.out.println("Hallo: " + hallo);
            if (!ConvertSquareToPiece(court[isOnSquare + (i * reverse)], false).equals("0")) {
                return false;
            }
            CheckForCheck((isOnSquare + (i * reverse)) + "");
            if (isInCheck) {
                return false;
            }
        }
        return true;
    }

    boolean CheckPawn() {
        int reverse = 1;
        int startRow = 8;
        int endRow = 15;
        if (toMove.equals("white")) {
            reverse = -1;
            startRow = 48;
            endRow = 55;
        }
        if (isOnSquare >= startRow && isOnSquare <= endRow) {
            if (CheckInFront(isOnSquare, moveToSquare, (2 * reverse))) {
                enPassant = "true"; //alternativ hätte man hier auch EnPassantable() starten können, aber so ist EnPassant Möglichkeit beenden einfacher, glaube ich zumindest
                System.out.println("Pawn moves! 2");
                return true;
            }
        }
        if(CheckInFront(isOnSquare, moveToSquare, reverse)){
            return true;
        }
            if (CheckEnPassant(reverse)) {
                return true;
            }

        return CheckPawnTake(reverse);
        }

    boolean CheckEnPassant(int reverse){
        if (isOnSquare + (7 * reverse) == enPassantSquare || isOnSquare + (9 * reverse) == enPassantSquare) {
            System.out.println("EN PASSANT!!!");
            court[enPassantSquare + (8 * reverse * -1)] = "0";
            return court[moveToSquare].equals("0");
        }
        return false;
    }

    boolean CheckPawnTake(int reverse){
        if (isOnSquare + (7 * reverse) == moveToSquare || isOnSquare + (9 * reverse) == moveToSquare) {
            System.out.println("PAWN TAKES!!!");
            return !court[moveToSquare].equals("0");
        }
        return false;
    }

    boolean CheckInFront(int toCheckStart, int toCheckEnd, int frontOrBack){
        frontOrBack *= 8;
        if (toCheckStart + frontOrBack == toCheckEnd) {
            return true;
        }
        ErrorType("Der Bauer kann sich nicht bewegen, weil eine Figur im Weg ist!");
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
        if(toCheckEnd == -1) {return ColumRowCheckCheck(toCheckStart, add);}
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

    boolean ColumRowCheckCheck(int toCheckStart, int add){
        //sehr sehr fies, weil bei row wird alles gecheckt bis ne Figur im weg steht
        int limit = 64;
        int minimum = 0;
        if (add == 1 || add == -1) {
            minimum = GetRow(toCheckStart);
            limit = minimum + 7;
        }
        String neededColor = ReverseColor(ConvertPieceColor(toCheckStart));
        if (court[toCheckStart].equals("0")) {
            neededColor = ReverseColor(toMove);
        }
        for (int i = add; i <= limit - toCheckStart && i >= minimum - toCheckStart; i += add) {
            String potentialChecker = ConvertSquareToPiece(court[toCheckStart + i], true);
            if (potentialChecker.equals(neededColor + " Rook") || potentialChecker.equals(neededColor + " Queen")) {
                System.out.println(potentialChecker + " Check from: " + ConvertSquareBack(toCheckStart + i) + "\nCheck to: " + ConvertPieceColor(toCheckStart) + " King!");
                return true;
            }
        }
        if (add > 0) {
            add *= -1;
            return ColumRowCheckCheck(toCheckStart, add);
        }
        return false;
    }

    boolean CheckForColumnRowCheck(int toCheck){
        return CheckBetweenColumnRow(toCheck, -1, "row") || CheckBetweenColumnRow(toCheck, -1, "column");
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

    boolean CheckOneDiagonal(int toCheckStart, int toCheckEnd, int add){
        //exponential
        for (int i = add; i < 100; i += add) {
            if (toCheckStart + i == toCheckEnd) {
                return true;
            }
            if (!(court[toCheckStart + add].equals("0"))) {
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

    boolean CheckDiagonalCheck(int toCheckStart){
        String neededColor = ReverseColor(ConvertPieceColor(toCheckStart));
        if (court[toCheckStart].equals("0")) {
            neededColor = ReverseColor(toMove);
        }
        int[] add = new int[]{7, -7, 9, -9};
        for (int i = add[step]; i <= 63 - toCheckStart && i >= -toCheckStart; i += add[step]) {
            String potentialChecker = ConvertSquareToPiece(court[toCheckStart + i],true);
            if( potentialChecker.equals(neededColor + " Bishop") || potentialChecker.equals(neededColor + " Queen")){
                System.out.println(potentialChecker + " Check from: " + ConvertSquareBack(toCheckStart + i) + "\nCheck to: " + ConvertPieceColor(toCheckStart) + " King!");
                step = 0;
                return true;
            }
            if (!potentialChecker.equals("0")) {
                break;
            }
        }
        if (step <= 2) {
            step++;
            return CheckDiagonalCheck(toCheckStart);
        }else{
            step = 0;
        }
        return false;
    }

    boolean CheckLMovementCheck(int toCheckStart){
        int reverse = 1;
        String neededColor = ReverseColor(ConvertPieceColor(toCheckStart));
        if (court[toCheckStart].equals("0")) {
            neededColor = ReverseColor(toMove);
        }
        int[] possibleMoves = new int[]{10, 15, 6, 17};
        //for (int i = 0; i < possibleMoves.length; i++) {
        for (int a = 0; a < 2; a++) {
            for (int possibleMove : possibleMoves) {
                if (toCheckStart + (possibleMove * reverse) >= 0 && toCheckStart + (possibleMove * reverse) < 64) {
                    String potentialChecker = ConvertSquareToPiece(court[toCheckStart + (possibleMove * reverse)], true);
                    if (potentialChecker.equals(neededColor + " Knight")) {
                        System.out.println("Knight check from: " + ConvertSquareBack(toCheckStart + (possibleMove * reverse)) + "\nCheck to: " + ConvertPieceColor(toCheckStart));
                        return true;
                    }
                }
            }
            reverse = -1;
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

    String ReverseColor(String color){
        switch (color){
            case "white" -> {return "black";}
            case "black" -> {return "white";}
        }
        return "ERROR!!";
    }

    void CheckBoard(){
         for (int i = 0; i < court.length; i++){
             System.out.println(i + ": " + court[i]);
        }
         /*for (String s : court) {
         System.out.println(s);
         }*/
    }

    void ErrorType(String error){
        if (errorType.isEmpty()) {
            errorType = error;
        }
    }
}
