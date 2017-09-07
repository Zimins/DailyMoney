package com.zapps.dailymoney.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    @BindView(R.id.spinner) Spinner viewModeSpinner;
    @BindView(R.id.tv_withdraw) TextView withdrawText;
    @BindView(R.id.tv_remains) TextView remainsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);
        setToolbar();

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

        ArrayAdapter<CharSequence> modesAdapter = ArrayAdapter.createFromResource(MainActivity
                .this, R.array.main_modes, android.R.layout.simple_spinner_dropdown_item);
        viewModeSpinner.setAdapter(modesAdapter);


    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
    @OnItemSelected(R.id.spinner)
    public void onSpinnerClicked(int pos) {
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
        withdrawText.setText(sumOfMonth + getString(R.string.unit_won));
        remainsText.setText(remain + getString(R.string.unit_won));
    }
}
