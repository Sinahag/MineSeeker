package ca.cmpt276.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.cmpt276.mineseeker.model.Game;
import ca.cmpt276.mineseeker.model.GameManager;
import ca.cmpt276.mineseeker.model.Settings;

/**
 * The MainActivity Java class is the main menu of the game. This provides the logic for all the
 * buttons shown in the main menu.
 */
public class MainActivity extends AppCompatActivity {
    private Game Game;
    private GameManager Games;
    private Settings Configurations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Games = GameManager.getInstance();
        Configurations = Settings.getInstance();
        setGameButton();
        setOptionsButton();
        setHelpButton();
    }

    private void setHelpButton() {
        Button btn = findViewById(R.id.help_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = HelpActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setOptionsButton() {
        Button btn = findViewById(R.id.options_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = OptionsActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setGameButton() {
        Button btn = findViewById(R.id.playgame_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"Congrats",Toast.LENGTH_SHORT).show();
                Intent intent = GamerMode.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }


}
