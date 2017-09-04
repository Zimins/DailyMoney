package com.zapps.dailymoney.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zapps.dailymoney.DistinctInfo;
import com.zapps.dailymoney.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Zimincom on 2017. 9. 4..
 */

public class OverviewListAdapter extends RecyclerView.Adapter<OverviewListAdapter.ViewHolder> {

    ArrayList<DistinctInfo> infos;

    public OverviewListAdapter(ArrayList<DistinctInfo> infos) {
        this.infos = infos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.dateText.setText(infos.get(position).getItemName() + "");
        String sumText = NumberFormat.getNumberInstance(Locale.KOREA)
                .format(infos.get(position).getSum());
        holder.sumOfDay.setText(sumText + "원");
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateText;
        TextView sumOfDay;

        public ViewHolder(View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.tv_date);
            sumOfDay = (TextView) itemView.findViewById(R.id.tv_sum);
        }
    }

}
