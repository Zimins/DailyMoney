package com.zapps.dailymoney.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.zapps.dailymoney.R;
import com.zapps.dailymoney.fragments.MonthlyViewFragment;
import com.zapps.dailymoney.fragments.OverviewFragment;
import com.zapps.dailymoney.fragments.TodayViewFragment;
import com.zapps.dailymoney.items.SMSItem;

import io.realm.Realm;
import io.realm.RealmChangeListener;

import static com.zapps.dailymoney.R.id.spinner;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    Spinner viewModeSpinner;
    TextView withdrawText;
    TextView remainsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: 2017. 9. 4. oncreate
        // TODO: 2017. 9. 4. won, 원 통일 (영문 ,한글 )
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        withdrawText = (TextView) findViewById(R.id.tv_withdraw);
        remainsText = (TextView) findViewById(R.id.tv_remains);

        viewModeSpinner = (Spinner) findViewById(spinner);
        ArrayAdapter<CharSequence> modesAdapter = ArrayAdapter.createFromResource(MainActivity
                .this, R.array.main_modes, android.R.layout.simple_spinner_dropdown_item);
        viewModeSpinner.setAdapter(modesAdapter);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                setMonthResult();
            }
        });
        viewModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos == 0) {
                    TodayViewFragment fragment = new TodayViewFragment();
                    android.app.FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.replace(R.id.main_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else if (pos == 1) {

                    MonthlyViewFragment fragment = new MonthlyViewFragment();
                    android.app.FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.replace(R.id.main_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (pos == 2) {

                    OverviewFragment fragment = new OverviewFragment();
                    android.app.FragmentTransaction transaction = getFragmentManager()
                            .beginTransaction();
                    transaction.replace(R.id.main_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMonthResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMonthResult() {
        // TODO: 2017. 9. 4. budget 설정에서 가져오기  + month 할당
        int budget = 100000;
        int sumOfMonth = realm.where(SMSItem.class)
                .equalTo("month", 9)
                .sum("withdrawAmount").intValue();
        int remain = budget - sumOfMonth;
        // TODO: 2017. 9. 4. won, 원 구별하여 사용 (통일)
        withdrawText.setText(sumOfMonth + "won");
        remainsText.setText(remain + "won");
    }
}
