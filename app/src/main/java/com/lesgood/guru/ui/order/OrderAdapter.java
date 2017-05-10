package com.lesgood.guru.ui.order;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Order;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PlaceholderFragment fragment;

    private final List<Order> orders = new ArrayList<>();

    public OrderAdapter(PlaceholderFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_order, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((OrderViewHolder)holder).bind(orders.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserItemClicked(orders.get(position));
            }
        });
    }

    private void onUserItemClicked(Order order) {
        fragment.showDetailOrder(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void onOrderAdded(Order order) {
        orders.add(order);
        notifyItemChanged(orders.size()-1);
    }

    public void onOrderChanged(Order order) {
        int index = orders.indexOf(order);
        if(index > -1) {
            orders.set(index, order);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void clearList() {
        orders.clear();
    }
}
