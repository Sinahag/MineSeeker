package ca.cmpt276.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import java.util.concurrent.TimeUnit;

/**
 * WelcomePage class provides the functionality of having a welcome animation on app start-up
 * This Java class provides the methods in linking the animation file with the layout resource supporting
 * different animations. This class also provides the methods of skipping such animation
 */
public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        ImageView imageView = findViewById(R.id.IntroView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.welcome_animation);
        imageView.setAnimation(animation);
        setSkipButton();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try{
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
    private void setSkipButton() {
        Button btn = findViewById(R.id.SkipWelcome);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}