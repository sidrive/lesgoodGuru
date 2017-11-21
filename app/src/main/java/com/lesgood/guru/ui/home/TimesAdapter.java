package com.lesgood.guru.ui.home;

import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.TimeSchedule;
import java.util.ArrayList;
import java.util.List;


public class TimesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HomeFragment fragment;

    private final List<TimeSchedule> items = new ArrayList<>();

    public TimesAdapter(HomeFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_time, parent, false);
        return new TimesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((TimesViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClicked(items.get(position)));

       /* ((TimesViewHolder) holder).imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showDeleteItem(items.get(position));

            }
        });*/
    }

    private void onItemClicked(TimeSchedule item) {
        Log.e("onItemClicked", "TimesAdapter" + item.getTime());
    }

    /*private void onItemDeleteClicked(String item){
        fragment.showDeleteItem(item);
    }*/

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void onItemAdded(TimeSchedule item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void onItemChanged(TimeSchedule item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {

        }
    }

    public void onItemRemoved(String item){
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
