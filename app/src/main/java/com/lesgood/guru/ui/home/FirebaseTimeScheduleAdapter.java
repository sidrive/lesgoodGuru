package com.lesgood.guru.ui.home;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.lesgood.guru.data.model.TimeSchedule;
import com.lesgood.guru.util.Utils;

/**
 * Developer at Geekgarden.
 * Created by sim-x on 1/29/18.
 * Website geekgarden.id .
 * More info  geekgardendev@gmail.com.
 */

public class FirebaseTimeScheduleAdapter extends FirebaseRecyclerAdapter<TimeSchedule,TimesViewHolder> {
  private HomeFragment fragment;
  public FirebaseTimeScheduleAdapter(Class<TimeSchedule> modelClass, int modelLayout,
      Class<TimesViewHolder> viewHolderClass, Query ref,HomeFragment _homeFragment) {
    super(modelClass, modelLayout, viewHolderClass, ref);
    this.fragment = _homeFragment;
  }

  @Override
  protected void populateViewHolder(TimesViewHolder holder, TimeSchedule time,
      int i) {
    holder.tvTime.setText(Utils.longToString(time.getStartTime())+" - "+Utils.longToString(time.getEndTime()));
    holder.tvDay.setText(Utils.dayFormated(time.getDay()));
    holder.itemView.setOnClickListener(v -> {
      fragment.deleteTimeSchedule(time.getSchedule_id());
    });
  }
}
