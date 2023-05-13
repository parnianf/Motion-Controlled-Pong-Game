package CPS.CPSCA2.Pong.Activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import CPS.CPSCA2.Pong.Loop.GameLoop;
import CPS.CPSCA2.R;

public class GameActivity extends AppCompatActivity {
    GameView gameView;
    String sensorType;
    GameLoop gameLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.activity_game);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        gameView = (GameView) findViewById(R.id.game_view);
        Pair<Integer, Integer> screen = new Pair<>(displayMetrics.widthPixels, displayMetrics.heightPixels);
        gameLoop = new GameLoop(gameView, 16, screen);
        System.out.println("before start");
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
}
