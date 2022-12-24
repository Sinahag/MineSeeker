package ca.cmpt276.mineseeker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import ca.cmpt276.mineseeker.model.Game;
import ca.cmpt276.mineseeker.model.GameManager;
import ca.cmpt276.mineseeker.model.Settings;

/**
 * GamerMode Java class provides the main functionality of the code
 * The XML file correlates to the game pad layout when you start the game
 * There are two singletons that are used within this class for synchronization within the entire
 * application. Games is the GameManager which holds records of all historical games and add the
 * newGame upon winning. The configurations is a Setting that is propagated from the options menu
 * to set the games number of rows and columns and the number of bombs to be shown on the map.
 * Finally the view is populated and update using the Game class instance newGame, where we update
 * the numbers shown on the screen and detecting whether a tile is a bomb or simply dirt.
 */
public class GamerMode extends AppCompatActivity {
    private Game newGame;
    private GameManager Games;
    private Settings configurations;
    Button[][] buttons;
    private int NUM_ROWS;
    private int NUM_COLS;
    private int NUM_BOMBS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        configurations = Settings.getInstance();
        Games = GameManager.getInstance();
        newGame = new Game(configurations);
        NUM_ROWS = configurations.getNumRows();
        NUM_COLS = configurations.getNumCols();
        NUM_BOMBS = newGame.getNumBombs();
        buttons = new Button[NUM_ROWS][NUM_COLS];

        updateNumBombsText();
        updateNumScansText();
        updateNumTimesPlayedText();
        updateBestScoreText();
        setBackButton();

        populateButtons();
    }

    private void updateNumBombsText() {
        TextView tv = findViewById(R.id.numRemainingBombsText);
        tv.setText(String.format("%d of %d Bombs",
                (NUM_BOMBS-newGame.getNumBombs()),
                NUM_BOMBS));
    }

    private void updateNumScansText() {
        TextView tv = findViewById(R.id.scansUsedText);
        tv.setText(String.format("Scans Used: %d",
                newGame.getNumScans()));
    }

    private void updateNumTimesPlayedText(){
        TextView tv = findViewById(R.id.timesPlayedText);
        tv.setText(String.format("Times Played: %d",
                Games.getNumGamesPlayed()));
    }

    private void updateBestScoreText(){
        TextView tv = findViewById(R.id.bestScoreText);
        tv.setText(String.format("Best Score: %s",
                Games.getBestScoreInGame(newGame.getRows(),newGame.getColumns())));
    }


    private void setBackButton() {
        Button btn = findViewById(R.id.backButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","message");
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, GamerMode.class);
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForButtons);

        for (int row = 0; row < NUM_ROWS; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setText(" ");

                // Make text not clip on small buttons
                button.setPadding(0, 0, 0, 0);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int col, int row) {
        Button button = buttons[row][col];

        // Lock Button Sizes:
        setButtonSizes();

        // Does not scale image.
//    	button.setBackgroundResource(R.drawable.action_lock_pink
        // Scale image to button: Only works in JellyBean!
        // Image from Crystal Clear icon set, under LGPL
        // http://commons.wikimedia.org/wiki/Crystal_Clear
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap;

        if (newGame.valueVisited(row,col)<=1) {

            newGame.scanTile(row, col);

            if(newGame.valueVisited(row,col)==1){
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.action_bomb_unscanned);
            }else if(newGame.valueVisited(row,col)==2) {
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.action_bomb_tile);
            }else{
                originalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.action_dirt_tile);
            }


            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            // Change text on button:
            for (int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    if (newGame.valueVisited(i, j) >= 2) {
                        int value = newGame.getSpot(i, j);
                        button = buttons[i][j];
                        button.setText("" + value);
                    }
                }
            }
        }
        updateNumBombsText();
        updateNumScansText();

        if (newGame.getNumBombs() == 0){
            FragmentManager manager = getSupportFragmentManager();
            CongratulationsFragment dialog = new CongratulationsFragment();
            dialog.setCurrentGame(newGame);
            dialog.show(manager,"MessageDialog");
        }
    }

    private void setButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }






}
