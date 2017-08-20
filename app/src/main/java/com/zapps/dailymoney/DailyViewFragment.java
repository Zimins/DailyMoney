package com.zapps.dailymoney;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zapps.dailymoney.adapters.DailyListAdapter;
import com.zapps.dailymoney.items.SMSItem;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class DailyViewFragment extends Fragment {

    RecyclerView dailyList;
    DailyListAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<SMSItem> items;
    Realm realm;

    TextView dateText;

    Calendar calendar;
    int month;
    int day;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        realm = Realm.getDefaultInstance();

        RecyclerView dailyList;


        setDate();

        items = new ArrayList<>();
        items.clear();
        items.addAll(realm.where(SMSItem.class).equalTo("month", month).equalTo("day", day)
                .findAll());

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        dailyList = (RecyclerView) getActivity().findViewById(R.id.daily_list);
        dailyList.setLayoutManager(layoutManager);
        adapter = new DailyListAdapter(items, dailyList);
        dailyList.setAdapter(adapter);


        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                items.clear();
                items.addAll(realm.where(SMSItem.class).equalTo("month", month).equalTo("day", day)
                        .findAll());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setDate() {
        dateText = (TextView) getActivity().findViewById(R.id.tv_date);
        calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH) + 1;//month starts from 0

        dateText.setText(month + "/" + "\n" + day);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
