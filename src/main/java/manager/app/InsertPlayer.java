package manager.app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class InsertPlayer extends BaseActivity {

    private int first_time_name = 0;
    private final ArrayList<String> alreadyExist = new ArrayList<>();
    private ArrayList<String[]> allName;

    private EditText name;
    private CheckBox goalkeeper;
    private MaterialButton insert;

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

        dbHelper = new MySQLiteHelper(getApplicationContext());
        readableDatabase = dbHelper.getReadableDatabase();
        writableDatabase = dbHelper.getWritableDatabase();
    }


    @Override
    protected void onStart() {
        super.onStart();

        insert.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 1200));
        name.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 800));
        goalkeeper.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 1100));

        loadExistingPlayers();

        name.setOnClickListener(v -> {
            if (first_time_name == 0) {
                name.setText("");
                first_time_name = 1;
            }
        });

        insert.setOnClickListener(v -> handleInsert());
    }

    @Override
    public void onBackPressed() {
        name.setText("");
        goalkeeper.setChecked(false);
        navigateTo(ManagePlayer.class);
    }

    private void loadExistingPlayers() {
        allName = dbHelper.getAllPlayer(readableDatabase);
        alreadyExist.clear();
        for (String[] player : allName) {
            alreadyExist.add(player[3]);
        }
    }

    private void handleInsert() {
        if (name.getText().length() == 0) {
            return;
        }

        boolean alreadyExistName = alreadyExist.contains(String.valueOf(name.getText()));
        int goalKeeper = goalkeeper.isChecked() ? 1 : 0;

        if (!alreadyExistName) {
            dbHelper.insertPlayer(writableDatabase, String.valueOf(name.getText()), "00000000", goalKeeper);
            dbHelper.close();
            writableDatabase.close();

            name.setText("");
            goalkeeper.setChecked(false);

            Toast.makeText(getApplicationContext(), "Giocatore inserito", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            Toast.makeText(getApplicationContext(), "Nome inserito già esistente nel Database", Toast.LENGTH_SHORT).show();
        }
    }


}


