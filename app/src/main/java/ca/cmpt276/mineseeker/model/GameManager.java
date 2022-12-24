package ca.cmpt276.mineseeker.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * The GameManager Java class allows us to keep track of the games we play.
 * The GameManager always keeps track of the best game out of the games we played, as every
 * game that is won is compared to the best game. If you win the game with less scans than the
 * previous best game then GameManager automatically replaces the old best game with your best game
 * The best game is organized by board size so winning on the smallest board will not replace your
 * game on the biggest board
 */

public class GameManager {
    public static final String NONE = "None";
    ArrayList<Game> GamesPlayed;
    private static GameManager instance;

    private GameManager(){
        GamesPlayed = new ArrayList<>();
    }

    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    // reset the array of games when we reset in options menu
    public GameManager resetGamesPlayed(){
        instance=null;
        return new GameManager();
    }

    public String getBestScore(int row, int col){
        int bestGame = getBestGame(row,col);
        if (bestGame>=0) {
            return GamesPlayed.get(bestGame).getRecord();
        }
        return "No " + row + "x" + col + " game";
    }


    public String getBestScoreInGame(int row, int col){
        int bestGame = getBestGame(row,col);
        if (bestGame>=0) {
            return GamesPlayed.get(bestGame).inGameRecord();
        }
        return NONE;
    }

    private int getBestGame(int row, int col){
        int bestGame = -1;
        boolean isEmpty = true;
        for(int i=0; i<GamesPlayed.size();i++){
            if (GamesPlayed.get(i).getColumns()==col && GamesPlayed.get(i).getRows()==row){
                if (bestGame==-1){ // if its the first
                    bestGame=i;
                }
                if (GamesPlayed.get(i).getNumScans()<=GamesPlayed.get(bestGame).getNumScans()){
                    bestGame = i;
                    isEmpty=false;
                }
            }
        }
        if (!isEmpty) {
            return bestGame;
        }
        return -1;
    }

    public void addGame(Game game){
        GamesPlayed.add(game);
    }

    public int getNumGamesPlayed(){
        return GamesPlayed.size();
    }



}
