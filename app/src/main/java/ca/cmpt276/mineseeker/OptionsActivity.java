package ca.cmpt276.mineseeker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.mineseeker.model.Game;
import ca.cmpt276.mineseeker.model.GameManager;
import ca.cmpt276.mineseeker.model.Settings;

/**
 * OptionsActivity Java class is built to supplement the functionality of the Options page
 * This page allows the user to change the amount of tiles and bombs that will be in the game
 * This page also shows the high score of each game separated by the board size option.
 * This high score can also be reset using the button functionality that is also provided in this class
 */
public class OptionsActivity extends AppCompatActivity {
    private Settings Configurations;
    private GameManager Games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);
        Configurations = Settings.getInstance();
        Games = GameManager.getInstance();

        // Enable action "up"
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.options);

        // define the buttons and spinners
        setBoardSizeSpinner();
        setBombSpinner();
        setResetButton();

        // populate the list of best past games
        populateListView();
    }

    private void setBoardSizeSpinner() {
        Spinner spinner = findViewById(R.id.spinnerBoardSize);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.board_size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(Configurations.getIndexOfBoardSize());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int row = Configurations.getBoardSizes()[i][0];
                int col = Configurations.getBoardSizes()[i][1];
                Configurations.setNumRows(row);
                Configurations.setNumCols(col);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}

        });

    }

    private void setBombSpinner() {
        Spinner spinner = findViewById(R.id.spinnerNumBombs);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.num_bombs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(Configurations.getIndexOfBombCount());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int bombs = Configurations.getBombCounts()[i];
                Configurations.setNumBombs(bombs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}

        });
    }

    private void setResetButton() {
        Button btn = findViewById(R.id.buttonResetTimesPlayes);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Games = Games.resetGamesPlayed();
                populateListView();
            }
        });
    }

    private void populateListView() {
        String[] listData = new String[Configurations.getBoardSizes().length];
//         Create list of items
        for(int i=0;i<Configurations.getBoardSizes().length; i++) {
            listData[i] = Games.getBestScore(
                    Configurations.getBoardSizes()[i][0],
                    Configurations.getBoardSizes()[i][1]
            );
        }

        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,   // activity context
                R.layout.items, // layout to use
                listData);      // data

        // Configure the list view
        ListView list = findViewById(R.id.listGamesPlayed);
        list.setAdapter(adapter);
    }


    public static Intent makeIntent(Context context){
        return new Intent(context, OptionsActivity.class);
    }
}
