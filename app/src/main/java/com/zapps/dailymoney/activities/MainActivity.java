package com.zapps.dailymoney.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.zapps.dailymoney.R;
import com.zapps.dailymoney.adapters.DailyListAdapter;
import com.zapps.dailymoney.items.SMSItem;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    RecyclerView dailyList;
    LinearLayoutManager linearLayoutManager;
    ArrayList<SMSItem> smsItems;
    Calendar calendar;
    TextView dateText;

    int month;
    int date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> modesAdapter = ArrayAdapter.createFromResource(MainActivity
                .this, R.array.main_modes, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(modesAdapter);

        setDateText();

        Realm.init(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        smsItems = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager
                .VERTICAL, false);
        dailyList = (RecyclerView) findViewById(R.id.daily_list);
        dailyList.setLayoutManager(linearLayoutManager);
        dailyList.setAdapter(new DailyListAdapter(smsItems, dailyList));

    }


    private void setDateText() {
        dateText = (TextView) findViewById(R.id.tv_date);
        calendar = Calendar.getInstance();

        date = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH) + 1;//month starts from 0

        Log.d("date", date + "/" + month);
        dateText.setText(month + "/" + "\n" + date);
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
