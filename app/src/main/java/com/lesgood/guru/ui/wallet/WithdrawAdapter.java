package com.lesgood.guru.ui.wallet;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Skill;
import com.lesgood.guru.data.model.Withdraw;
import com.lesgood.guru.ui.skill.SkillViewHolder;
import java.util.ArrayList;
import java.util.List;


public class WithdrawAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private WalletActivity activity;

    private final List<Withdraw> items = new ArrayList<>();

    public WithdrawAdapter(WalletActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_withdraw, parent, false);
        return new WithdrawViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Withdraw withdraw = getWithdraw(position);

        ((WithdrawViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClicked(items.get(position)));

    }

    private void onItemClicked(Withdraw withdraw) {
    }
    private Withdraw getWithdraw(int pos){
        return items.get(pos);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getMinRate(){
        int rate = 0;
        if (items.size()>0){
            rate = items.get(0).getSaldo();
            for (int i=0;i<items.size();i++){
                int rateNew = items.get(i).getSaldo();
                if (rateNew < rate){
                    rate = rateNew;
                }
            }
        }
        return rate;
    }

    public void onItemAdded(Withdraw withdraw) {
        items.add(withdraw);
        notifyDataSetChanged();
    }

    public void onItemChanged(Withdraw withdraw) {
        int index = items.indexOf(withdraw);
        if(index > -1) {
            items.set(index, withdraw);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(Withdraw withdraw){
       items.remove(withdraw);
    }

    public void clearList() {
        items.clear();
    }
}
