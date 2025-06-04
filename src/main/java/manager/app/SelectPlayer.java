package manager.app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectPlayer extends BaseActivity {

    public static int game = 0;
    private ArrayList<String[]> listComplete = new ArrayList<>();
    private ArrayList<Integer> idSelect = new ArrayList<>(), idGoalKeepers = new ArrayList<>(), idPlayers = new ArrayList<>();
    private ArrayList<String> playersName = new ArrayList<>(), GoalKeepersName = new ArrayList<>(), list = new ArrayList<>();

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
        navigateTo(MainActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mylist.setAnimation(Utils.getAnimation(0, 0, -5000, 0, 700));
        next.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));

        listComplete = dbHelper.getAllPlayer(writableDatabase);
        dbHelper.close();
        writableDatabase.close();

        list.clear();
        for (String[] player : listComplete) {
            list.add(player[2] + " - " + player[1] + " - " + player[3]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list);
        mylist.setAdapter(adapter);
        mylist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mylist.setOnItemClickListener((parent, view, position, id) -> {});

        next.setOnClickListener(v -> {
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
                        navigateTo(OrganizeTeam.class);
                    }

                    if (game == 7) {
                        OrganizeTeam.id_selected_to_org = idSelect;
                        OrganizeTeam.id_selected_gio_to_org = idPlayers;
                        OrganizeTeam.id_selected_por_to_org = idGoalKeepers;
                        OrganizeTeam.name_gio_org = playersName;
                        OrganizeTeam.name_por_org = GoalKeepersName;
                        OrganizeTeam.game_org = 7;
                        navigateTo(OrganizeTeam.class);
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
        });
    }
}


