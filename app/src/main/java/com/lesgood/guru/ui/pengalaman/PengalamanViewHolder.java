package com.lesgood.guru.ui.pengalaman;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Pengalaman;
import com.lesgood.guru.data.model.Prestasi;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PengalamanViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_name)
    TextView txtTitle;

    @Bind(R.id.img_remove)
    ImageView imgRemove;

    private View itemView;

    public PengalamanViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Pengalaman item) {
        txtTitle.setText(item.getTitle());
    }
}

