package ca.cmpt276.mineseeker.model;

import java.util.Random;

/**
 * Game Java class provides the functions for the logic of the game
 * This class also provides all the arrays that store where the bombs are placed, if you visited
 * a tile, and if that visited place is a bomb. This also keeps track of every bomb in your row and
 * column so the calculations is always precise. The game logic is in this class.
 */

public class Game {
    private final int columns;
    private final int rows;
    private int bombs;
    public int startingBombs;
    private int[][] Spots;
    private int[][] Bombs;
    private int[][] Visited;
    private int numScans;

    public Game(int numRows, int numCols, int numBombs){
        columns = numCols;
        rows = numRows;
        bombs = numBombs;
        startingBombs = numBombs;
        Spots = new int[rows][columns];
        Bombs = new int[rows][columns]; // initialized to 0's (since int is primitive type)
        Visited = new int[rows][columns];
        // Visited is
        // 0 if not visited
        // 1 if its a bomb
        // 2 if its a scanned bomb
        // 3 if its dirt
        placeBombs();
        updateGameBoardCounts();
    }

    public Game(Settings settings){
        columns = settings.getNumCols();
        rows = settings.getNumRows();
        bombs = settings.getNumBombs();
        startingBombs = settings.getNumBombs();
        Spots = new int[rows][columns];
        Bombs = new int[rows][columns]; // initialized to 0's (since int is primitive type)
        Visited = new int[rows][columns];
        placeBombs();
        updateGameBoardCounts();
    }

    private void placeBombs(){
        Random bomb = new Random();
        int i=0;
        int x, y;
        while(i < bombs){
            x = bomb.nextInt(rows);
            y = bomb.nextInt(columns);
            if (Bombs[x][y] != 1) {
                Bombs[x][y] = 1;
                i++;
            }
        }
    }

    public int isBomb(int row, int col){
        return Bombs[row][col];
    }

// only increase when showing the number

    public void foundBomb(int row, int col){
        if (isBomb(row,col)==1){
            Bombs[row][col] = 0;
            updateGameBoardCounts();
            bombs--;
        }
    }

    public void scanTile(int row, int col) {
        if (Visited[row][col] == 0 && isBomb(row, col) == 0) { // case when dirt
            Visited[row][col] = 3;
            numScans++;
        }else if (Visited[row][col]==1){ // if its scanned but not showing value
            Visited[row][col]=2;
            numScans++;
        }else if (Visited[row][col] == 0 && isBomb(row, col) == 1){ // case when bomb is first visited
            Visited[row][col] = 1;
        }
        foundBomb(row,col);
    }

    public int getNumScans(){
        return numScans;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    // returns a printable record of the game size, scans and bombs
    public String getRecord(){
        return "Size:" + rows + "x" + columns + " Scans:" + numScans + " Bombs:" + startingBombs;
    }

    public String inGameRecord(){
        return numScans + " with " + startingBombs + " bombs";
    }

    private void updateGameBoardCounts(){
        for(int i = 0; i<Spots.length; i++){
            for(int j = 0; j<Spots[0].length; j++){
                Spots[i][j] = numBombsInRow(i) + numBombsInColumn(j);
            }
        }
    }

    public int valueVisited(int row, int col){
        return Visited[row][col];
    }

    public int getNumBombs(){
        return bombs;
    }

    public int getSpot(int row, int col){
        return Spots[row][col];
    }


    private int numBombsInColumn(int col) {
        int count = 0;
        for(int i = 0; i< rows; i++){
            if (Bombs[i][col] == 1){
                count++;
            }
        }
        return count;
    }

    private int numBombsInRow(int row){
        int count = 0;
        for(int i = 0; i< columns; i++){
            if (Bombs[row][i] == 1){
                count++;
            }
        }
        return count;
    }

}
