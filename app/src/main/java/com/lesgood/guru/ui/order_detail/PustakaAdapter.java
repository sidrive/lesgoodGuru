package com.lesgood.guru.ui.order_detail;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Pustaka;
import com.lesgood.guru.data.remote.OrderService;
import com.lesgood.guru.ui.order_detail.PustakaAdapter.Holder;
import com.lesgood.guru.util.TypefacedTextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Developer at Geekgarden.
 * Created by sim-x on 1/15/18.
 * Website geekgarden.id .
 * More info  geekgardendev@gmail.com.
 */

public class PustakaAdapter extends Adapter<Holder> {


  private OrderDetailActivity activity;
  private List<Pustaka> pustakas;

  public PustakaAdapter(OrderDetailActivity activity,
      OrderService orderService) {
    this.activity = activity;
    this.pustakas = new ArrayList<>();
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pustaka, null);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(Holder holder, int i) {
    Pustaka p = getPustaka(i);
    Log.e("onBindViewHolder", "PustakaAdapter" + p.getName());
    holder.tvTitle.setText(p.getName());
    holder.itemView.setOnClickListener(v -> {
      activity.viewPustaka(p);
    });
  }

  @Override
  public int getItemCount() {
    return pustakas.size();
  }
  public Pustaka getPustaka(int pos){
    return pustakas.get(pos);
  }
  public void onAddPustaka(Pustaka pustakaList){
    pustakas.add(pustakaList);
    notifyDataSetChanged();

  }

  public class Holder extends ViewHolder {
    @Bind(R.id.tvTitle)
    TypefacedTextView tvTitle;
    public Holder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
