package manager.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FirstFrame extends Activity {

    private ImageView buttonFive, buttonSeven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_frame);
        buttonFive = findViewById(R.id.buttonFive);
        buttonSeven = findViewById(R.id.buttonSeven);
    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonFive.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));

        buttonFive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectPlayer.game = 5;
                Intent newIntent = new Intent(FirstFrame.this, SelectPlayer.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_pre, R.anim.animation_post).toBundle());
                System.gc();
            }
        });

        buttonSeven.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));

        buttonSeven.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectPlayer.game = 7;
                Intent newIntent = new Intent(FirstFrame.this, SelectPlayer.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_pre, R.anim.animation_post).toBundle());
                System.gc();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(FirstFrame.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_pre, R.anim.animation_post).toBundle());
        System.gc();
    }

}


