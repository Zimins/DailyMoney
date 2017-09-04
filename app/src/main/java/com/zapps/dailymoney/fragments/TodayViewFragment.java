package com.zapps.dailymoney.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zapps.dailymoney.R;
import com.zapps.dailymoney.adapters.TodayListAdapter;
import com.zapps.dailymoney.items.SMSItem;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class TodayViewFragment extends Fragment implements View.OnClickListener {

    RecyclerView dailyList;
    TodayListAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<SMSItem> items;
    Realm realm;

    TextView monthText;
    TextView dateText;
    ImageButton yesterdayButton;
    ImageButton tomorrowButton;

    Calendar calendar;
    int month;
    int day;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        realm = Realm.getDefaultInstance();
        // TODO: 2017. 9. 4. oncreate helper

        RecyclerView dailyList;

        monthText = (TextView) getActivity().findViewById(R.id.tv_month);
        dateText = (TextView) getActivity().findViewById(R.id.tv_date);
        yesterdayButton = (ImageButton) getActivity().findViewById(R.id.button_yesterday);
        tomorrowButton = (ImageButton) getActivity().findViewById(R.id.button_tomorrow);

        yesterdayButton.setOnClickListener(this);
        tomorrowButton.setOnClickListener(this);

        setToday();

        items = new ArrayList<>();
        items.clear();
        items.addAll(realm.where(SMSItem.class).equalTo("month", month + 1).equalTo("day", day)
                .findAll());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .VERTICAL, false);
        dailyList = (RecyclerView) getActivity().findViewById(R.id.daily_list);
        dailyList.setLayoutManager(layoutManager);
        adapter = new TodayListAdapter(items, dailyList);
        dailyList.setAdapter(adapter);

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                listData(month, day);
            }
        });
    }

    // TODO: 2017. 9. 4. method name

    private void setToday() {

        calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DATE);
        month = calendar.get(Calendar.MONTH);//month starts from 0

        setDateText(month, day);
    }

    private void setDateText(int month, int day) {
        monthText.setText((month + 1) + "월");
        dateText.setText(day + "");
    }

    private void listData(int month, int day) {
        items.clear();
        items.addAll(realm.where(SMSItem.class).equalTo("month", month + 1).equalTo("day", day)
                .findAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // TODO: 2017. 9. 4. calender 로 변경해보기
        if (id == R.id.button_yesterday) {
            if (day > calendar.getActualMinimum(Calendar.DAY_OF_MONTH)) {
                setDateText(month, --day);
                listData(month, day);
            } else {
                calendar.set(Calendar.MONTH, --month);
                day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                setDateText(month, day);
            }
        } else if (id == R.id.button_tomorrow) {
            if (day < calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                setDateText(month, ++day);
                listData(month, day);
            } else {
                calendar.set(Calendar.MONTH, ++month);
                day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
                setDateText(month, day);
            }
        }
    }


}
