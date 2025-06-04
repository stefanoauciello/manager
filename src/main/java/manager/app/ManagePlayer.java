package manager.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ManagePlayer extends Activity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String[]> listComplete = new ArrayList<String[]>();
    private ArrayList<String> list = new ArrayList<String>();

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
        Intent newIntent = new Intent(ManagePlayer.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_pre, R.anim.animation_post).toBundle());
        System.gc();
    }

    @Override
    protected void onStart() {
        super.onStart();

        addPlayer.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        playersList.setAnimation(Utils.getAnimation(0, 0, -5000, 0, 700));

        addPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newIntent = new Intent(ManagePlayer.this, InsertPlayer.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_pre, R.anim.animation_post).toBundle());
                System.gc();
            }
        });

        listComplete = dbHelper.getAllPlayer(writableDatabase);
        dbHelper.close();
        writableDatabase.close();

        for (int i = 0; i < listComplete.size(); i++) {
            list.add(listComplete.get(i)[2] + " - " + listComplete.get(i)[1] + " - " + listComplete.get(i)[3]);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, list);
        playersList.setAdapter(adapter);
        playersList.setChoiceMode(ListView.CHOICE_MODE_NONE);
        playersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditPlayer.idToEdit = Integer.valueOf(listComplete.get(position)[2]);
                Intent newIntent = new Intent(ManagePlayer.this, EditPlayer.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_pre, R.anim.animation_post).toBundle());
                System.gc();
            }
        });
    }
}


