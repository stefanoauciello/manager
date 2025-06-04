package manager.app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Manager";
    private static final String sDbPath = "/data/data/" + MainActivity.class.getPackage().getName() + "/databases/" + DATABASE_NAME;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        String CREATE_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS players ( id INTEGER PRIMARY KEY AUTOINCREMENT , name VARCHAR(50), phone_number VARCHAR(50) , portiere NUMBER)";
        db.execSQL(CREATE_PLAYER_TABLE);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertPlayer(SQLiteDatabase db, String name, String phone_number, int portiere) {
        db.beginTransaction();
        String INSERT = "INSERT INTO players(name,phone_number,portiere) values ('" + name + "','" + (phone_number) + "'," + portiere + ");";
        db.execSQL(INSERT);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<String[]> getAllPlayer(SQLiteDatabase db) {
        ArrayList<String[]> players = new ArrayList<String[]>();
        db.beginTransaction();
        Cursor mCount = db.rawQuery("select portiere,name,id from players", null);
        mCount.moveToFirst();
        if (mCount != null && mCount.getCount() > 0) {
            do {
                String[] player = new String[4];
                player[0] = String.valueOf(mCount.getInt(0));
                if (mCount.getInt(0) == 1) {
                    player[1] = "Portiere";
                } else {
                    player[1] = "Giocatore";
                }
                player[2] = String.valueOf(mCount.getInt(2));
                player[3] = String.valueOf(mCount.getString(1));
                players.add(player);
            } while (mCount.moveToNext());
        }
        db.endTransaction();
        return players;
    }

    public String[] getPlayerById(SQLiteDatabase db, int id) {
        String[] player = new String[4];
        db.beginTransaction();
        Cursor mCount = db.rawQuery("select portiere,name,id,phone_number from players where id = " + id, null);
        int lunghezza = mCount.getCount();
        mCount.moveToFirst();
        if (mCount != null && mCount.getCount() > 0) {
            do {
                int portiere = mCount.getInt(0);
                String name = mCount.getString(1);
                int id_player = mCount.getInt(2);
                String numero = mCount.getString(3);
                player[0] = String.valueOf(portiere);
                player[1] = String.valueOf(name);
                player[2] = String.valueOf(id_player);
                player[3] = String.valueOf(numero);
            } while (mCount.moveToNext());
        }
        db.endTransaction();
        return player;
    }

    public int deletePlayerById(SQLiteDatabase db, int id) {
        db.beginTransaction();
        String[] stringArray = new String[1];
        stringArray[0] = String.valueOf(id);
        int success = db.delete("players", " id = ? ", stringArray);
        db.setTransactionSuccessful();
        db.endTransaction();
        return success;
    }

    public void updatePlayer(SQLiteDatabase db, String[] player) {
        String name = player[0];
        String numb = player[1];
        String port = player[2];
        String id = player[3];
        db.beginTransaction();
        String INSERT = "UPDATE players SET name = '" + name + "' , phone_number = '" + (numb) + "' , portiere =  " + Integer.valueOf(port) + " where id = " + Integer.valueOf(id);
        db.execSQL(INSERT);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}