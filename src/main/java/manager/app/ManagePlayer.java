package manager.app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ManagePlayer extends BaseActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String[]> listComplete = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();

    private ListView playersList;
    private ImageView addPlayer;

    private MySQLiteHelper dbHelper;
    private SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_player);

        addPlayer = findViewById(R.id.addPlayer);
        playersList = findViewById(R.id.playersList);

        dbHelper = new MySQLiteHelper(getApplicationContext());
        writableDatabase = dbHelper.getWritableDatabase();
    }

    @Override
    public void onBackPressed() {
        navigateTo(MainActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        addPlayer.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        playersList.setAnimation(Utils.getAnimation(0, 0, -5000, 0, 700));

        addPlayer.setOnClickListener(v -> navigateTo(InsertPlayer.class));

        listComplete = dbHelper.getAllPlayer(writableDatabase);
        dbHelper.close();
        writableDatabase.close();

        list.clear();
        for (String[] player : listComplete) {
            list.add(player[2] + " - " + player[1] + " - " + player[3]);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, list);
        playersList.setAdapter(adapter);
        playersList.setChoiceMode(ListView.CHOICE_MODE_NONE);
        playersList.setOnItemClickListener((parent, view, position, id) -> {
            EditPlayer.idToEdit = Integer.valueOf(listComplete.get(position)[2]);
            navigateTo(EditPlayer.class);
        });
    }
}


