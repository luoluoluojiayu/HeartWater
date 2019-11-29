package com.example.heartwater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static java.sql.DriverManager.println;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("heart还在运行：" + System.currentTimeMillis());
            }
        }, 1000, 1000);
    }

    private void init() {
        findViewById(R.id.btn_bessel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChartActivity.class));
            }
        });

        findViewById(R.id.btn_heart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HeartActivity.class));
            }
        });

        findViewById(R.id.btn_block).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BlocksActivity.class));
            }
        });
    }
}
