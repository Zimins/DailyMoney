package com.zapps.dailymoney.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zapps.dailymoney.DayInfo;
import com.zapps.dailymoney.R;
import com.zapps.dailymoney.adapters.DailyListAdapter;
import com.zapps.dailymoney.items.SMSItem;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Zimincom on 2017. 8. 14..
 */

public class MonthlyViewFragment extends Fragment {

    @BindView(R.id.daily_list) RecyclerView listView;
    DailyListAdapter listAdapter;

    ArrayList<DayInfo> dayInfos;
    Realm realm;
    RealmResults<SMSItem> items;

    int month;
    int day;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realm = Realm.getDefaultInstance();

        // TODO: 2017. 9. 4. oncreate 정리 (recycler view)
        dayInfos = new ArrayList<>();

        month = Calendar.getInstance().get(Calendar.MONTH)+1;
        day = Calendar.getInstance().get(Calendar.DATE);

        createDayInfos();

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                dayInfos.clear();
                createDayInfos();
                listAdapter.notifyDataSetChanged();
            }
        });

        bindRecycler(dayInfos);
    }

    private void bindRecycler(ArrayList<DayInfo> dayInfos) {
        listView = (RecyclerView) getActivity().findViewById(R.id.daily_list);
        listAdapter = new DailyListAdapter(dayInfos);
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .VERTICAL, false));
        listView.setAdapter(listAdapter);
    }

    private void createDayInfos() {
         for (int i = 0; i < 31; i++) {
            items = realm.where(SMSItem.class)
                    .equalTo("month", month)
                    .equalTo("day", i + 1).findAll();
            int sum = items.sum("withdrawAmount").intValue();
            Log.i("sum of day", sum + "won");
            DayInfo dayInfo = new DayInfo(month, i+1, sum);

            if (sum != 0) {
                dayInfos.add(dayInfo);
            }
        }
    }
}
