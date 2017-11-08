package com.lesgood.guru.ui.prestasi;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Prestasi;

import java.util.ArrayList;
import java.util.List;


public class PrestasiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PrestasiActivity activity;

    private final List<Prestasi> items = new ArrayList<>();

    public PrestasiAdapter(PrestasiActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_prestasi, parent, false);
        return new PrestasiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((PrestasiViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClicked(items.get(position)));

        ((PrestasiViewHolder) holder).imgRemove.setOnClickListener(
            v -> activity.showDeleteItem(items.get(position)));
    }

    private void onItemClicked(Prestasi item) {
    }

    private void onItemDeleteClicked(Prestasi item){
        activity.showDeleteItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void onItemAdded(Prestasi item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void onItemChanged(Prestasi item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(Prestasi item){
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
