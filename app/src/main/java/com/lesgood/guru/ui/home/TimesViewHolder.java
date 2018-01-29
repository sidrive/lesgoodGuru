package com.lesgood.guru.ui.home;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Pengalaman;
import com.lesgood.guru.data.model.TimeSchedule;
import com.lesgood.guru.util.Utils;

public class TimesViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvDay)
    TextView tvDay;
    public TimesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

