package com.lesgood.guru.ui.wallet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Withdraw;
import com.lesgood.guru.util.Utils;

public class WithdrawViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_skill)
    TextView txtSkill;

    @Bind(R.id.txt_level)
    TextView txtLevel;

    @Bind(R.id.txt_price)
    TextView txtPrice;


    private View itemView;

    public WithdrawViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Withdraw withdraw) {
        txtSkill.setText(Utils.longToDateFormat(withdraw.getCreateAt()));
        txtLevel.setText(withdraw.getStatus());
        txtPrice.setText(Utils.getRupiah(withdraw.getSaldo()));
    }
}

