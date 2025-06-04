package manager.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectPlayer extends Activity {

    public static int game = 0;
    private ArrayList<String[]> listComplete = new ArrayList<String[]>();
    private ArrayList<Integer> idSelect = new ArrayList<Integer>(), idGoalKeepers = new ArrayList<Integer>(), idPlayers = new ArrayList<Integer>();
    private ArrayList<String> playersName = new ArrayList<String>(), GoalKeepersName = new ArrayList<String>(), list = new ArrayList<String>();

    private ImageView next;
    private ListView mylist;

    private MySQLiteHelper dbHelper;
    private SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_player);

        next = findViewById(R.id.next);
        mylist = findViewById(R.id.mylist);

        dbHelper = new MySQLiteHelper(getApplicationContext());
        writableDatabase = dbHelper.getWritableDatabase();
    }

    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(SelectPlayer.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_pre, R.anim.animation_post).toBundle());
        System.gc();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mylist.setAnimation(Utils.getAnimation(0, 0, -5000, 0, 700));
        next.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));

        listComplete = dbHelper.getAllPlayer(writableDatabase);
        dbHelper.close();
        writableDatabase.close();

        for (int i = 0; i < listComplete.size(); i++) {
            list.add(listComplete.get(i)[2] + " - " + listComplete.get(i)[1] + " - " + listComplete.get(i)[3]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list);
        mylist.setAdapter(adapter);
        mylist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idSelect.clear();
                idGoalKeepers.clear();
                idPlayers.clear();
                playersName.clear();
                GoalKeepersName.clear();

                int len = mylist.getCount();
                SparseBooleanArray checked = mylist.getCheckedItemPositions();
                boolean goalKeepers = false;
                boolean players = false;
                int numberGoalKeeper = 0;
                int numberPlayers = 0;

                for (int i = 0; i < len; i++) {
                    if (checked.get(i)) {
                        idSelect.add(Integer.valueOf(listComplete.get(i)[2]));
                        if (Integer.valueOf(listComplete.get(i)[0]) == 1) {
                            numberGoalKeeper = numberGoalKeeper + 1;
                            idGoalKeepers.add(Integer.valueOf(listComplete.get(i)[2]));
                            GoalKeepersName.add(listComplete.get(i)[3]);
                        }
                        if (Integer.valueOf(listComplete.get(i)[0]) == 0) {
                            numberPlayers = numberPlayers + 1;
                            idPlayers.add(Integer.valueOf(listComplete.get(i)[2]));
                            playersName.add(listComplete.get(i)[3]);
                        }
                    }
                }
                if (game == 5 && numberGoalKeeper == 2 && numberPlayers == 8) {
                    goalKeepers = true;
                    players = true;
                }
                if (game == 7 && numberGoalKeeper == 2 && numberPlayers == 12) {
                    goalKeepers = true;
                    players = true;
                }
                if (goalKeepers && players) {

                    if (game == 5) {
                        OrganizeTeam.id_selected_to_org = idSelect;
                        OrganizeTeam.id_selected_gio_to_org = idPlayers;
                        OrganizeTeam.id_selected_por_to_org = idGoalKeepers;
                        OrganizeTeam.name_gio_org = playersName;
                        OrganizeTeam.name_por_org = GoalKeepersName;
                        OrganizeTeam.game_org = 5;
                        Intent newIntent = new Intent(SelectPlayer.this, OrganizeTeam.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(newIntent);
                        System.gc();
                    }

                    if (game == 7) {
                        OrganizeTeam.id_selected_to_org = idSelect;
                        OrganizeTeam.id_selected_gio_to_org = idPlayers;
                        OrganizeTeam.id_selected_por_to_org = idGoalKeepers;
                        OrganizeTeam.name_gio_org = playersName;
                        OrganizeTeam.name_por_org = GoalKeepersName;
                        OrganizeTeam.game_org = 7;
                        Intent newIntent = new Intent(SelectPlayer.this, OrganizeTeam.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(newIntent);
                        System.gc();
                    }
                }
                if (!goalKeepers && !players) {
                    if (game == 5) {
                        Toast.makeText(getApplicationContext(), "seleziona 2 portieri e 8 giocatori.", Toast.LENGTH_LONG).show();
                    }
                    if (game == 7) {
                        Toast.makeText(getApplicationContext(), "seleziona 2 portieri e 12 giocatori.", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }
}


