package manager.app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by step1 on 06/03/2017.
 */

public class OrganizeTeam extends BaseActivity implements SensorEventListener {

    private static final int SHAKE_THRESHOLD = 7000;
    public static int fromSend = 0, howSend = 0, game_org = 0, shake_active = 0;
    public static ArrayList<Integer> id_selected_por_to_org = new ArrayList<Integer>(), id_selected_gio_to_org = new ArrayList<Integer>(), id_selected_to_org = new ArrayList<Integer>();
    public static ArrayList<String> name_gio_org = new ArrayList<String>(), name_por_org = new ArrayList<String>(), totale = new ArrayList<String>();
    private TextView goalKeeper1, player1_A, player2_A, player3_A, player4_A, player5_A,
            player6_A, goalKeeper2, player1_B, player2_B, player3_B, player4_B,
            player5_B, player6_B;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate;
    private float last_x, last_y, last_z;
    private ImageView shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organize_team);

        goalKeeper1 = findViewById(R.id.goalKeeper1);
        player1_A = findViewById(R.id.player1_A);
        player2_A = findViewById(R.id.player2_A);
        player3_A = findViewById(R.id.player3_A);
        player4_A = findViewById(R.id.player4_A);
        player5_A = findViewById(R.id.player5_A);
        player6_A = findViewById(R.id.player6_A);
        goalKeeper2 = findViewById(R.id.goalKeeper2);
        player1_B = findViewById(R.id.player1_B);
        player2_B = findViewById(R.id.player2_B);
        player3_B = findViewById(R.id.player3_B);
        player4_B = findViewById(R.id.player4_B);
        player5_B = findViewById(R.id.player5_B);
        player6_B = findViewById(R.id.player6_B);
        shake = findViewById(R.id.shake);
    }

    @Override
    protected void onStart() {
        super.onStart();

        shake.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));

        if (fromSend == 0) {
            setOffLabelSend();
        } else if (fromSend == 1) {
            setOnLabelSend();
        }

        shake_active = 1;

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        String to_show_before = "";

        for (int i = 0; i < name_gio_org.size(); i++) {
            to_show_before = to_show_before + " " + i + " " + name_gio_org.get(i) + "\n";
            totale.add(name_gio_org.get(i));
        }

        totale.add(name_por_org.get(0));
        totale.add(name_por_org.get(1));

        shuffle_func(totale);
        shake.setOnClickListener(v -> shuffle_func(totale));

        Toast.makeText(getApplicationContext(), "Effettua subito lo screen e condividilo con gli amici!!", Toast.LENGTH_LONG).show();
    }

    public void shuffle_func(ArrayList<String> totale_in) {
        Collections.shuffle((totale_in));
        shake_active = 1;

        if (game_org == 5) {
            goalKeeper1.setText(totale_in.get(0));
            player1_A.setText(totale_in.get(1));
            player2_A.setText(totale_in.get(2));
            player3_A.setText(totale_in.get(3));
            player4_A.setText(totale_in.get(4));
            player5_A.setText("");
            player6_A.setText("");
            goalKeeper2.setText(totale_in.get(5));
            player1_B.setText(totale_in.get(6));
            player2_B.setText(totale_in.get(7));
            player3_B.setText(totale_in.get(8));
            player4_B.setText(totale_in.get(9));
            player5_B.setText("");
            player6_B.setText("");
        }

        if (game_org == 7) {
            goalKeeper1.setText(totale_in.get(0));
            player1_A.setText(totale_in.get(1));
            player2_A.setText(totale_in.get(2));
            player3_A.setText(totale_in.get(3));
            player4_A.setText(totale_in.get(4));
            player5_A.setText(totale_in.get(5));
            player6_A.setText(totale_in.get(6));
            goalKeeper2.setText(totale_in.get(7));
            player1_B.setText(totale_in.get(8));
            player2_B.setText(totale_in.get(9));
            player3_B.setText(totale_in.get(10));
            player4_B.setText(totale_in.get(11));
            player5_B.setText(totale_in.get(12));
            player6_B.setText(totale_in.get(13));
        }

        goalKeeper1.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        player1_A.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        player2_A.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        player3_A.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        player4_A.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        player5_A.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));
        player6_A.setAnimation(Utils.getAnimation(-3000, 0, 0, 0, 700));

        goalKeeper2.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        player1_B.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        player2_B.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        player3_B.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        player4_B.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        player5_B.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
        player6_B.setAnimation(Utils.getAnimation(3000, 0, 0, 0, 700));
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    if (shake_active == 1) {
                        shake_active = 0;
                        shuffle_func(totale);
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onBackPressed() {
        navigateTo(SelectPlayer.class);
    }

    public void setOnLabelSend() {
    }

    public void setOffLabelSend() {
    }

}
