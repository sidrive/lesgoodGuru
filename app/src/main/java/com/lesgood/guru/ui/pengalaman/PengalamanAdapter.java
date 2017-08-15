package com.lesgood.guru.ui.pengalaman;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Pengalaman;

import java.util.ArrayList;
import java.util.List;


public class PengalamanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PengalamanActivity activity;

    private final List<Pengalaman> items = new ArrayList<>();

    public PengalamanAdapter(PengalamanActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_prestasi, parent, false);
        return new PengalamanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((PengalamanViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(items.get(position));
            }
        });

        ((PengalamanViewHolder) holder).imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showDeleteItem(items.get(position));

            }
        });
    }

    private void onItemClicked(Pengalaman item) {
    }

    private void onItemDeleteClicked(Pengalaman item){
        activity.showDeleteItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void onItemAdded(Pengalaman item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void onItemChanged(Pengalaman item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(Pengalaman item){
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
