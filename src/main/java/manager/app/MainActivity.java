package manager.app;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends BaseActivity {

    private ImageView managePlayers, createTeam, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managePlayers = findViewById(R.id.managePlayers);
        createTeam = findViewById(R.id.createTeam);
        exit = findViewById(R.id.exit);
    }

    @Override
    protected void onStart() {
        super.onStart();

        managePlayers.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        createTeam.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        exit.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));

        managePlayers.setOnClickListener(v -> navigateTo(ManagePlayer.class));

        createTeam.setOnClickListener(v -> navigateTo(FirstFrame.class));

        exit.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Termina")
                .setMessage("Sei sicuro di voler chiudere l'app?")
                .setPositiveButton("Si", (dialog, which) -> MainActivity.this.finish())
                .setNegativeButton("No", null)
                .show();
    }
}


