package com.lesgood.guru.ui.home;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Days;
import com.lesgood.guru.ui.pengalaman.PengalamanViewHolder;
import java.util.ArrayList;
import java.util.List;


public class DaysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HomeFragment fragment;

    private final List<Days> items = new ArrayList<>();

    public DaysAdapter(HomeFragment activity) {
        this.fragment = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_day, parent, false);
        return new DaysViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((DaysViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClicked(items.get(position)));

        ((DaysViewHolder) holder).imgRemove.setOnClickListener(
            v -> onshowTimePicker(items.get(position)));
    }

    private void onItemClicked(Days item) {
        fragment.showDetailListSchedule(item.getName());
    }

    private void onshowTimePicker(Days item){
        fragment.showTimePicker(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void onItemAdded(Days item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void onItemChanged(Days item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {

        }
    }

    public void onItemRemoved(Days item){
        items.remove(item);
        notifyDataSetChanged();
    }

    public void onitemremovedindex(int position){
        items.remove(position);
        notifyDataSetChanged();
    }

    public void clearList() {
        items.clear();
    }
}
