package com.zapps.dailymoney.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import io.realm.RealmChangeListener;

public class MainActivity extends AppCompatActivity {

    RecyclerView dailyList;
    DailyListAdapter adapter;
    Realm realm;
    LinearLayoutManager linearLayoutManager;
    ArrayList<SMSItem> smsItems;
    Calendar calendar;
    TextView dateText;

    int month;
    int day;

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
        realm = Realm.getDefaultInstance();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        smsItems = new ArrayList<>();

        smsItems.clear();
        smsItems.addAll(realm.where(SMSItem.class).equalTo("month", month).equalTo("day", day)
                .findAll());

        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager
                .VERTICAL, false);
        dailyList = (RecyclerView) findViewById(R.id.daily_list);
        dailyList.setLayoutManager(linearLayoutManager);
        adapter = new DailyListAdapter(smsItems, dailyList);
        dailyList.setAdapter(adapter);

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                smsItems.clear();
                smsItems.addAll(realm.where(SMSItem.class).equalTo("month", month).equalTo("day", day)
                        .findAll());
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void setDateText() {
        dateText = (TextView) findViewById(R.id.tv_date);
        calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH) + 1;//month starts from 0

        dateText.setText(month + "/" + "\n" + day);
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
