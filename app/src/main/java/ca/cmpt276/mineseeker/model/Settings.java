package ca.cmpt276.mineseeker.model;

import java.util.Arrays;

/**
 * The Settings Java class provides the game with all the settings we choose. On the XML file we
 * have buttons that correspond with calling the Settings functions to change up how the game is
 * played. We have setters for setting the bomb count and board size and getters for when the
 * game is played and need to know the layout of the game.
 */

public class Settings {
    private int[][] board_size = {{4,6},{5,10},{6,15},{7,20}};
    private int[] bomb_counts = {6, 10, 15, 20};

    private int numBombs;
    private int numRows;
    private int numCols;
    private static Settings instance;

    private Settings(){
        numBombs = bomb_counts[0];
        numRows = board_size[0][0];
        numCols = board_size[0][1];
    }

    public static Settings getInstance(){
        if (instance == null){
            instance = new Settings();
        }
        return instance;
    }

    public int[][] getBoardSizes(){
        return board_size;
    }

    public int[] getBombCounts(){
        return bomb_counts;
    }

    public int getIndexOfBombCount(){
        for(int i=0; i<bomb_counts.length;i++){
            if (bomb_counts[i]==numBombs) {
                return i;
            }
        }
        return 0;
    }

    public int getIndexOfBoardSize(){
        for(int i=0; i<board_size.length; i++){
            if (board_size[i][0]==numRows) {
                return i;
            }
        }
        return 0;
    }

    public int getNumCols(){
        return numCols;
    }
    public int getNumBombs(){
        return numBombs;
    }
    public int getNumRows(){
        return numRows;
    }

    public void setNumBombs(int numMines) {
        this.numBombs = numMines;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

}
