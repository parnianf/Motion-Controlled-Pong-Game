package CPS.CPSCA2.Pong.Activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import CPS.CPSCA2.Pong.Coordinate.Coordinate;
import CPS.CPSCA2.Pong.Logic.Advanced.*;
import CPS.CPSCA2.R;

public class AdvancedGameActivity extends AppCompatActivity implements SensorEventListener {
    GameView gameView;
    GameLoopAdvanced gameLoopAdvanced;
    private long timestamp;
    private long timestamp1;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initializeSensors();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        gameView = (GameView) findViewById(R.id.game_view);
        Pair<Integer, Integer> screen = new Pair<>(displayMetrics.widthPixels, displayMetrics.heightPixels);
        gameLoopAdvanced = new GameLoopAdvanced(gameView, (float) 0.016, screen);
        gameLoopAdvanced.start();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onBackPressed() {
        System.out.println("onBackPressed");
        super.onBackPressed();
        gameLoopAdvanced.endLoop();
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

//        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (timestamp == 0) timestamp = event.timestamp;
        if (timestamp1 == 0 ) timestamp1 = event.timestamp;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            Coordinate acceleration = new Coordinate(event.values[0] * 2 * displayMetrics.widthPixels, event.values[1] * 2 * displayMetrics.widthPixels, event.values[2] * 2 * displayMetrics.widthPixels);
            gameLoopAdvanced.updatePaddleXAcceleration(acceleration, (double) (event.timestamp - timestamp) / 1000000000);
            gameLoopAdvanced.updateBallAcceleration(event.values[2]);
            timestamp = event.timestamp;// acc m/s^2 * 100cm/1m * widthPixels/50cm = px/s^2
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            Coordinate angularVelocity = new Coordinate(event.values[0], event.values[1], event.values[2]);
            gameLoopAdvanced.updatePaddleAngularVelocity(angularVelocity, (double) (event.timestamp - timestamp1)/1000000000);
            timestamp1 = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
