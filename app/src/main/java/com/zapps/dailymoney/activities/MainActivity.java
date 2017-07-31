package com.zapps.dailymoney.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> modesAdapter = ArrayAdapter.createFromResource(MainActivity
                .this, R.array.main_modes, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(modesAdapter);

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
        dailyList.setAdapter(new DailyListAdapter(smsItems, dailyList));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
