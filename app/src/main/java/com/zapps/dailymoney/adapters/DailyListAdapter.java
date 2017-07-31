package com.zapps.dailymoney.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zapps.dailymoney.R;
import com.zapps.dailymoney.items.SMSItem;

import java.util.ArrayList;

/**
 * Created by Zimincom on 2017. 7. 31..
 */

public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder>{

    ArrayList<SMSItem> smsItems;

    public DailyListAdapter(ArrayList<SMSItem> smsItems) {
        this.smsItems = smsItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms,
                parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return smsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        ImageView itemImage;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.tv_name);
            itemPrice = (TextView) itemView.findViewById(R.id.tv_price);
            itemImage = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }
}
