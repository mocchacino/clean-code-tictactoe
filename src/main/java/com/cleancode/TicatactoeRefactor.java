package com.cleancode;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicatactoeRefactor {
    static String[] board;
    static String turn;
    static int NUM_OF_ROWS = 3;
    static int NUM_OF_COLUMNS = 3;
    static String winner;
    static int numInput = 0;

    static void createCells(int row, int col){
        int totalCells = row * col;
        board = new String[totalCells];

        fillNumInCells(totalCells);
    }

    static void fillNumInCells(int totalCells){
        int valueCell = 0;
        for (int cell = 0; cell < totalCells; cell++) {
            valueCell++;
            board[cell] = String.valueOf(valueCell);
        }
    }

    static void printBoard(int row, int col){
        int cell = 0;

        for(int currentRow = 0; currentRow < row; currentRow++){
            System.out.println("|---|---|---|");
            for(int currentColumn = 0; currentColumn < col; currentColumn++){
                System.out.print("| " + board[cell] + " ");
                cell++;
            }
            System.out.println("|");
        }
        System.out.println("|---|---|---|");
    }

    static void straightHorizontal(int rows, int cols){
        String line = "";
        int cell = 0;
        for(int currentRow = 0; currentRow < rows; currentRow++){
            cell = (currentRow * rows);
            for(int currentCol = 0; currentCol < cols; currentCol++){
                line = line.concat(board[cell]);
                cell++;
            }
            checkingWin(line);
            line="";
        }
    }

    static void straightVertical(int rows, int cols){
        String line = "";
        int cell = 0;
        for(int currentCol = 0; currentCol < cols; currentCol++){
            for(int currentRow = 0; currentRow < rows; currentRow++){
                cell = (currentRow * rows) + (currentCol);
                line = line.concat(board[cell]);
            }
            checkingWin(line);
            line="";
        }
    }

    static void straightDiagonal(int rows, int cols){
        diagonalLeft(rows, cols);
        diagonalRight(rows,cols);
    }

    static void diagonalLeft(int rows, int cols){
        String line = "";
        int totalCell = rows * cols;
        int midCell = (totalCell - 1) / (rows - 1);
        int cell = 0;
        for(int currentRow = 0; currentRow < rows; currentRow++){
            line = line.concat(board[cell]);
            cell += midCell;
        }
        checkingWin(line);
    }

    static void diagonalRight(int rows, int cols){
        String line = "";
        int totalCell = rows * cols;
        int midCell = (totalCell - 1) / rows;
        int cell = 0;
        for(int currentRow = 0; currentRow < rows; currentRow++){
            cell += midCell;
            line = line.concat(board[cell]);
        }
        checkingWin(line);
    }

    static void checkingWin(String line){
        if (line.equals("XXX")) {
            winner =  "X";
        }else if(line.equals("OOO")){
            winner =  "O";
        }
    }

    static boolean isCellNotFilled(int cell){
        String cellValue = String.valueOf(cell + 1);
        if(Arrays.asList(board).contains(cellValue)){ //if cell contain num then its not filled
            return true;
        }else return false;
    }

    static boolean isLastCell(int cell, int totalCell){
        if (cell == (totalCell - 1)){
            return true;
        }else return false;
    }

    static boolean isCellAllFilled(){
        int totalcell = NUM_OF_ROWS * NUM_OF_COLUMNS;
        for (int cell = 0; cell < totalcell; cell++) {
            if (isCellNotFilled(cell)) {
                break;
            }else if (isLastCell(cell, totalcell)) {
                return true;
            }
        }
        return false;
    }

    static void anyWinner(){
        straightHorizontal(3,3);
        straightVertical(3,3);
        straightDiagonal(3,3);
        if(isCellAllFilled()){
            winner = "draw";
        }
    }

    static int inputNumCell(){
        Scanner inputScanner = new Scanner(System.in);
        int numInput = inputScanner.nextInt();

        return numInput;
    }

    static boolean isCellAvailable(int numInput){
        String cellValue = String.valueOf(numInput);
        int currentCell = numInput - 1;
        if (board[currentCell].equals(cellValue)) {
            return true;
        }else return false;
    }

    static String[] fillSelectedCell(int numInput){
        board[numInput - 1] = turn;
        return board;
    }

    static void changingTurn(){
        if (turn.equals("X")) {
            turn = "O";
        }else {
            turn = "X";
        }
    }

    static void printChangingTurn(){
        changingTurn();
        // To enter the X Or O at the exact place on board.
        System.out.println(
                turn + "'s turn; enter a slot number to place "
                        + turn + " in:");
    }

    static boolean isMoveAvailable(){
        if(numInput <= (NUM_OF_COLUMNS * NUM_OF_ROWS) && numInput >= 0 && winner == null){
            return true;
        }return false;
    }

    static void playTheTurn(int rows, int cols){
        try {
            while(isMoveAvailable()){
                numInput = inputNumCell();
                if (isCellAvailable(numInput)) {
                    playing(rows,cols);
                }
                else {
                    System.out.println("Slot already taken; re-enter slot number:");
                }
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input; re-enter slot number:");
        }
    }

    static void playing(int rows, int cols){
        board = fillSelectedCell(numInput);

        printBoard(rows,cols);
        anyWinner();

        printChangingTurn();
    }

    static void finalResult(){
        if (winner.equalsIgnoreCase("draw")) {
            printDraw();
        }else {
            printWinner(winner);
        }
    }

    static void printWinner(String winner){
        System.out.println("Congratulations! " + winner + "'s have won! Thanks for playing.");
    }

    static void printDraw(){
        System.out.println("It's a draw! Thanks for playing.");
    }

    public static void main(String[] args){
        turn = "X";

        System.out.println("Welcome to 3x3 Tic Tac Toe.");
        createCells(NUM_OF_ROWS, NUM_OF_COLUMNS);
        printBoard(NUM_OF_ROWS, NUM_OF_COLUMNS);

        System.out.println("X will play first. Enter a slot number to place X in:");

        playTheTurn(NUM_OF_ROWS, NUM_OF_COLUMNS);

        finalResult();
    }

}
