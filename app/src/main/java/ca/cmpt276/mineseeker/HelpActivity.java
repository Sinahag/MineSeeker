package ca.cmpt276.mineseeker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;
import android.graphics.Color;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * HelpActivity is the java class and supports the activity for the Help page.
 * This page displays how to play the game and authors with an hyperlink to the assignment document
 * The hyperlink functionality is all provided in the Java code.
 * All other text documentation and pictures are provided in the corresponding XML file.
 */

public class HelpActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
        TextView linkTextView = findViewById(R.id.helpHyperLink);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
        linkTextView.setLinkTextColor(Color.BLUE);

        // Enable action "up"
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.help);

    }

    public static Intent makeIntent(Context context){
        return new Intent(context, HelpActivity.class);
    }
}
