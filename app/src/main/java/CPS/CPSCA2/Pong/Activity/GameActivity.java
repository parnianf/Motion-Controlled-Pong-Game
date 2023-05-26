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

import java.util.Arrays;

import CPS.CPSCA2.Pong.Loop.GameLoop;
import CPS.CPSCA2.R;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    GameView gameView;
    GameLoop gameLoop;

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
        gameLoop = new GameLoop(gameView, (float) 0.016, screen);
        gameLoop.start();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onBackPressed() {
        System.out.println("onBackPressed");
        super.onBackPressed();
        gameLoop.endLoop();
    }

    private void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        System.out.println("SENSOR CHANGED");
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            // Obtain the screen density in pixels per inch
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float density = displayMetrics.density * 7000; // 160f is the standard DPI of a medium-density screen

// Convert meters to pixels using the density
            float meters = event.values[0]; // Example length in meters
            float pixels = meters * density;

            gameLoop.updatePaddleXAcceleration(pixels);
            gameLoop.updatePaddlePositionByAccelerometer();
//            System.out.println("acc : " + Arrays.toString(event.values));
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float angularVelocityZ = event.values[2];
            System.out.println("ANGULAR Velocity: " + angularVelocityZ);
            gameLoop.updatePaddleAngularVelocity(angularVelocityZ); //TODO: density?
            gameLoop.updatePaddlePositionByGyroscope();
//            System.out.println("GYRO : " + Arrays.toString(event.values));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
