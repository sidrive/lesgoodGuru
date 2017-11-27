package com.lesgood.guru.ui.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.lesgood.guru.R;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.util.DateFormatter;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.txt_day)
    TextView txtDay;

    @Bind(R.id.txt_month)
    TextView txtMonth;

    @Bind(R.id.txt_title)
    TextView txtTitle;

    @Bind(R.id.txt_status)
    TextView txtStatus;

    @Bind(R.id.txt_order_at)
    TextView txtOrderat;

    @Bind(R.id.txt_siswa)
    TextView txtSiswa;

    @Bind(R.id.txt_pertemuan)
    TextView txtPertemuan;

    @Bind(R.id.txt_price)
    TextView txtPrice;


    private View itemView;

    public OrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Order order) {

        txtTitle.setText(order.getTitle());

        int total = (int)(order.getTotal()+0.5d);

        txtPertemuan.setText(String.valueOf(order.getTotalPertemuan())+" Pertemuan");
        txtSiswa.setText(String.valueOf(order.getTotalSiswa())+" Siswa");

        txtPrice.setText("Rp."+toRupiah(total));
        txtDay.setText(DateFormatter.getDate(order.getPertemuanTime(), "dd"));
        txtMonth.setText(DateFormatter.getDate(order.getPertemuanTime(), "MMM"));
        txtOrderat.setText("ordered at "+DateFormatter.getDate(order.getOrdertime(), "dd-mm-yy, HH:mm"));
        if (order.getStatus().equals("pending_guru")){
            txtStatus.setText("Menunggu Konfirmasi Guru");
        }else if (order.getStatus().equals("pending_murid")){
            txtStatus.setText("Menunggu Pembayaran Siswa");
        }else if (order.getStatus().equals("cancel_guru")){
            txtStatus.setText("Dibatalkan Guru");
        }else if (order.getStatus().equals("cancel_murid")){
            txtStatus.setText("Dibatalkan Murid");
        }else {
           txtStatus.setText("SUCCESS");
        }
    }

    private String toRupiah(int amount){
        String angka = Integer.toString(amount);
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        String rupiah = rupiahFormat.format(Double.parseDouble(angka));
        return rupiah;
    }
}

