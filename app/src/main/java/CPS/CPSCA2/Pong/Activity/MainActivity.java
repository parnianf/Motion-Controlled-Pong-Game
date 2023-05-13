package CPS.CPSCA2.Pong.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import CPS.CPSCA2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
        setContentView(R.layout.activity_main);

        Intent newIntent = new Intent(this, GameActivity.class);
        startActivity(newIntent);

    }
}