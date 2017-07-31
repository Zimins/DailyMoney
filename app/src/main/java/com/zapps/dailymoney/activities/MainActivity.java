package com.zapps.dailymoney.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zapps.dailymoney.R;
import com.zapps.dailymoney.adapters.DailyListAdapter;
import com.zapps.dailymoney.items.SMSItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView dailyList;
    DailyListAdapter dailyListAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<SMSItem> smsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsItems = new ArrayList<>();

        smsItems.add(new SMSItem("coffee", 10000));
        smsItems.add(new SMSItem("coffee", 10000));
        smsItems.add(new SMSItem("coffee", 10000));
        smsItems.add(new SMSItem("coffee", 10000));
        smsItems.add(new SMSItem("coffee", 10000));
        smsItems.add(new SMSItem("coffee", 10000));
       

        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager
                .VERTICAL, false);
        dailyList = (RecyclerView) findViewById(R.id.daily_list);
        dailyList.setLayoutManager(linearLayoutManager);
        dailyList.setAdapter(new DailyListAdapter(smsItems));

    }
}
