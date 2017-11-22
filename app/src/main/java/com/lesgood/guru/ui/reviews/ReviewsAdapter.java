package com.lesgood.guru.ui.reviews;

/**
 * Created by ikun on 22/11/17.
 */

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Reviews;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ReviewsActivity activity;

    private final List<Reviews> items = new ArrayList<>();

    public ReviewsAdapter(ReviewsActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_reviews, parent, false);
        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ReviewsViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClicked(items.get(position)));

    }

    private void onItemClicked(Reviews item) {
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onItemAdded(Reviews item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void onItemChanged(Reviews item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(Reviews item){
        items.remove(item);
    }

    public void clearList() {
        items.clear();
    }
}
