package CPS.CPSCA2.Pong.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import CPS.CPSCA2.R;

public class MainActivity extends AppCompatActivity {

    Button normalGameBtn;
    Button advancedGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.activity_main);


        normalGameBtn = (Button) findViewById(R.id.normal_game);
        advancedGameBtn = (Button) findViewById(R.id.advanced_game);

        normalGameBtn.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, GameActivity.class);
            newIntent.putExtra("game_type","normal");
            startActivity(newIntent);
        });

        advancedGameBtn.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, GameActivity.class);
            newIntent.putExtra("game_type","advanced");
            startActivity(newIntent);
        });

//        Intent newIntent = new Intent(this, GameActivity.class);
//        startActivity(newIntent);

    }
}