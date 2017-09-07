package com.zapps.dailymoney.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zapps.dailymoney.R;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        new Timer().schedule(timerTask, 1500);

    }
}
