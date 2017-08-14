package com.zapps.dailymoney.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zapps.dailymoney.R;
import com.zapps.dailymoney.items.SMSItem;

import java.util.ArrayList;

import io.realm.Realm;


public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.ViewHolder> {

    private ArrayList<SMSItem> smsItems;
    private RecyclerView dailyList;
    Realm realm;

    {
        realm = Realm.getDefaultInstance();
    }

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SMSItem item = smsItems.get(position);

        if (item.isDetail) {
            holder.details.setVisibility(View.VISIBLE);
        } else {
            holder.details.setVisibility(View.GONE);
        }

        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(String.format("%d won", item.getWithdrawAmount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        item.isDetail = !item.isDetail;
                    }
                });
                notifyItemChanged(position);
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
        CardView itemCard;
        View details;

        ImageView itemImage;

        private ViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.tv_name);
            itemPrice = (TextView) itemView.findViewById(R.id.tv_price);
            itemCard = (CardView) itemView.findViewById(R.id.card_item);
            details = itemView.findViewById(R.id.details);
            itemImage = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }
}
