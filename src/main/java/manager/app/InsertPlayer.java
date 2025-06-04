package manager.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class InsertPlayer extends Activity {

    private int first_time_name = 0, first_time_number = 0;
    private ArrayList<String> alreadyExist = new ArrayList<String>();
    private ArrayList<String[]> allName;

    private EditText name;
    private CheckBox goalkeeper;
    private ImageView insert, nameLabel;

    private MySQLiteHelper dbHelper;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_player);

        insert = findViewById(R.id.insert);
        name = findViewById(R.id.name);
        goalkeeper = findViewById(R.id.goalkeeper);
        nameLabel = findViewById(R.id.nameLabel);

        dbHelper = new MySQLiteHelper(getApplicationContext());
        readableDatabase = dbHelper.getReadableDatabase();
        writableDatabase = dbHelper.getWritableDatabase();
    }


    @Override
    protected void onStart() {
        super.onStart();

        insert.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 1200));
        nameLabel.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        name.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 800));
        goalkeeper.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 1100));


        allName = dbHelper.getAllPlayer(readableDatabase);

        alreadyExist.clear();
        for (int i = 0; i < allName.size(); i++) {
            alreadyExist.add(allName.get(i)[3]);
        }

        name.setOnClickListener(v -> {
            if (first_time_name == 0) {
                name.setText("");
                first_time_name = 1;
            }
        });

        insert.setOnClickListener(v -> {
            if (name.getText().length() != 0 && name.getText() != null) {

                    Boolean already_exist = false;

                    if (alreadyExist.contains(String.valueOf(name.getText()))) {
                        already_exist = true;
                    }

                    boolean isGoalKepper = goalkeeper.isChecked();
                    int GoalKeeper = 0;
                    if (isGoalKepper) GoalKeeper = 1;

                    if (!already_exist) {

                        dbHelper.insertPlayer(writableDatabase, String.valueOf(name.getText()), "00000000", GoalKeeper);
                        dbHelper.close();
                        writableDatabase.close();

                        name.setText("");
                        goalkeeper.setChecked(false);

                        Toast.makeText(getApplicationContext(), "Giocatore inserito", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        if (already_exist) {
                            Toast.makeText(getApplicationContext(), "Nome inserito già esistente nel Database", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });
    }

    @Override
    public void onBackPressed() {
        name.setText("");
        goalkeeper.setChecked(false);
        Intent newIntent = new Intent(InsertPlayer.this, ManagePlayer.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_pre, R.anim.animation_post).toBundle());
        System.gc();
    }


}


