package com.lesgood.guru.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Days;
import com.lesgood.guru.data.model.Pengalaman;

public class DaysViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_name)
    TextView txtTitle;

    @Bind(R.id.img_remove)
    ImageView imgRemove;

    private View itemView;

    public DaysViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Days item) {
        txtTitle.setText(item.getName());
    }
}

