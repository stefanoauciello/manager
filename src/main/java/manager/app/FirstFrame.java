package manager.app;

import android.os.Bundle;
import android.widget.ImageView;

public class FirstFrame extends BaseActivity {

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

        buttonFive.setOnClickListener(v -> {
            SelectPlayer.game = 5;
            navigateTo(SelectPlayer.class);
        });

        buttonSeven.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));

        buttonSeven.setOnClickListener(v -> {
            SelectPlayer.game = 7;
            navigateTo(SelectPlayer.class);
        });
    }

    @Override
    public void onBackPressed() {
        navigateTo(MainActivity.class);
    }

}


