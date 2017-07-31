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


public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder>{

    private ArrayList<SMSItem> smsItems;
    private RecyclerView dailyList;

    public DailyListAdapter(ArrayList<SMSItem> smsItems, RecyclerView dailyList) {
        this.smsItems = smsItems;
        this.dailyList = dailyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms,
                parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SMSItem item = smsItems.get(position);

        if (item.isDetail) {
            holder.details.setVisibility(View.VISIBLE);
        } else {
            holder.details.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.isDetail = !item.isDetail;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return smsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;

        View details;

        ImageView itemImage;

        private ViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.tv_name);
            itemPrice = (TextView) itemView.findViewById(R.id.tv_price);

            details = itemView.findViewById(R.id.details);
            itemImage = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }
}
