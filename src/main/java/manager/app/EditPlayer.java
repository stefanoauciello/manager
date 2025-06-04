package manager.app;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class EditPlayer extends BaseActivity {

    public static int idToEdit = 0;
    private ArrayList<String> alreadyIDExist = new ArrayList<>(), alreadyExist = new ArrayList<>();
    private ArrayList<String[]> allName = new ArrayList<>();
    private String[] player;

    private EditText name;
    private CheckBox goalkeeper;
    private ImageView save, nameLabel, delete;

    private MySQLiteHelper dbHelper;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_player);

        nameLabel = findViewById(R.id.nameLabel);
        name = findViewById(R.id.name);
        goalkeeper = findViewById(R.id.goalkeeper);
        save = findViewById(R.id.save);
        delete = findViewById(R.id.delete);

        dbHelper = new MySQLiteHelper(getApplicationContext());
        readableDatabase = dbHelper.getReadableDatabase();
        writableDatabase = dbHelper.getWritableDatabase();
    }

    @Override
    public void onBackPressed() {
        navigateTo(ManagePlayer.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        allName = dbHelper.getAllPlayer(readableDatabase);

        alreadyExist.clear();
        alreadyIDExist.clear();

        for (String[] p : allName) {
            alreadyExist.add(p[3]);
            alreadyIDExist.add(p[2]);
        }

        name.setText("");
        goalkeeper.setChecked(false);
        save.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 1200));
        nameLabel.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        nameLabel.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        name.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 800));
        goalkeeper.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 1100));


        player = dbHelper.getPlayerById(writableDatabase, idToEdit);
        dbHelper.close();
        writableDatabase.close();

        name.setText(player[1]);

        if (player[0].equals("0")) {
            goalkeeper.setChecked(false);
        }
        if (player[0].equals("1")) {
            goalkeeper.setChecked(true);
        }

        save.setOnClickListener(v -> {
            if (name.getText().length() != 0 && name.getText() != null) {

                    boolean isGoalKeeper = goalkeeper.isChecked();
                    int goalkeeperNumber = 0;
                    if (isGoalKeeper) goalkeeperNumber = 1;
                    Boolean alreadyExist = false;

                    for (int idx = 0; idx < EditPlayer.this.alreadyExist.size(); idx++) {
                        if (EditPlayer.this.alreadyExist.get(idx).equals(String.valueOf(name.getText()))) {
                            alreadyExist = !alreadyIDExist.get(idx).equals(String.valueOf(idToEdit));
                        }
                    }

                    if (!alreadyExist) {
                        String[] player = new String[4];
                        player[0] = String.valueOf(name.getText());
                        player[1] = String.valueOf("00000000");
                        player[2] = String.valueOf(goalkeeperNumber);
                        player[3] = String.valueOf(idToEdit);

                        writableDatabase = dbHelper.getWritableDatabase();
                        dbHelper.updatePlayer(writableDatabase, player);
                        dbHelper.close();
                        writableDatabase.close();

                        name.setText("");
                        goalkeeper.setChecked(false);
                        Toast.makeText(getApplicationContext(), "Giocatore modificato", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "Nome inserito già esistente nel Database", Toast.LENGTH_SHORT).show();
                    }
                }
        });

        delete.setOnClickListener(v -> new AlertDialog.Builder(EditPlayer.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Elimina")
                .setMessage("Eliminare il giocatore?")
                .setPositiveButton("Si", (dialog, which) -> {
                    if (name.getText().length() != 0 && name.getText() != null) {
                        writableDatabase = dbHelper.getWritableDatabase();
                        dbHelper.deletePlayerById(writableDatabase, idToEdit);
                        dbHelper.close();
                        writableDatabase.close();
                        Toast.makeText(getApplicationContext(), "Giocatore eliminato correttamente.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show());

        dbHelper.close();
        readableDatabase.close();
    }
}




