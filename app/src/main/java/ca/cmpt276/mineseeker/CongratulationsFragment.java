package ca.cmpt276.mineseeker;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;

import ca.cmpt276.mineseeker.model.Game;
import ca.cmpt276.mineseeker.model.GameManager;

/**
 * CongratulationsFragment android fragment is a congratulatory message shown upon successful completion
 * of a game, it contains two emojis and a breakdown of your score.
 * This also has a confirmation OK button to allow the use to go back to the main menu
 */

public class CongratulationsFragment extends AppCompatDialogFragment {
    Game currentGame;

    public void setCurrentGame(Game newGame){
        currentGame= newGame;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the view to show
        GameManager Games = GameManager.getInstance();
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.congratulate_message_layout,null);

        TextView tv = v.findViewById(R.id.statisticsText);
        tv.setText(String.format("Game Statistics:\n%s",currentGame.getRecord()));

        // Create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Games.addGame(currentGame);
                getActivity().finish();
            }
        };

        // Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                        .setTitle("Congratulations - Winner")
                        .setView(v)
                        .setPositiveButton(android.R.string.ok, listener)
                        .create();
    }

}
