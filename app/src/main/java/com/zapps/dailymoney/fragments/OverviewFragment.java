package com.zapps.dailymoney.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zapps.dailymoney.DistinctInfo;
import com.zapps.dailymoney.R;
import com.zapps.dailymoney.adapters.OverviewListAdapter;
import com.zapps.dailymoney.items.SMSItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by Zimincom on 2017. 9. 4..
 */

public class OverviewFragment extends Fragment {

    Realm realm;

    RecyclerView sumListView;
    OverviewListAdapter adapter;

    ArrayList<String> distinctNames = new ArrayList<>();
    ArrayList<DistinctInfo> distinctInfos = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realm = Realm.getDefaultInstance();

        // TODO: 2017. 9. 4. oncreate 정리

        //아이템 이름을 검색(중복 없이)
        RealmResults<SMSItem>  distinctResults = realm.where(SMSItem.class).distinct("itemName");
        for (SMSItem item : distinctResults) {
            distinctNames.add(item.getItemName());
        }
        createDistinctInfos();

        bindRecycler();

        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                distinctInfos.clear();
                createDistinctInfos();
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void bindRecycler() {

        sumListView = (RecyclerView) getActivity().findViewById(R.id.sum_list);
        sumListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .VERTICAL, false));
        adapter = new OverviewListAdapter(distinctInfos, getContext());
        sumListView.setAdapter(adapter);
    }

    void createDistinctInfos () {
        for (String itemName : distinctNames) {
            int sum = realm.where(SMSItem.class)
                    .equalTo("itemName", itemName)
                    .equalTo("month", 9)
                    .sum("withdrawAmount").intValue();
            DistinctInfo info = new DistinctInfo(itemName, sum);
            distinctInfos.add(info);
        }

        Collections.sort(distinctInfos, new DecendingObj());
    }

    class DecendingObj implements Comparator<DistinctInfo> {
        @Override
        public int compare(DistinctInfo o1, DistinctInfo o2) {
            return o2.getSum() - o1.getSum();
        }
    }
}
