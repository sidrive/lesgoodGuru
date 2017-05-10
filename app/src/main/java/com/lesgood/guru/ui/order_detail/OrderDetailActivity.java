package com.lesgood.guru.ui.order_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.util.DateFormatter;

import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agus on 2/23/17.
 */

public class OrderDetailActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.rv_items)
    RecyclerView rvItems;

    @Bind(R.id.txt_customer_name)
    TextView txtCustomerName;

    @Bind(R.id.txt_date)
    TextView txtDate;

    @Bind(R.id.txt_order_id)
    TextView txtOrderId;

    @Bind(R.id.txt_status)
    TextView txtStatus;

    @Bind(R.id.txt_amount)
    TextView txtAmount;

    @Inject
    Order order;

    @Inject
    OrderDetailPresenter presenter;


    public static void startWithOrder(BaseActivity activity, Order order) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        BaseApplication.get(activity).createOrderDetailComponent(order);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
        BaseApplication.get(this).releaseOrderDetailComponent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getOrderDetailComponent()
                .plus(new OrderDetailActivityModule(this))
                .inject(this);
    }



    public void init(){
        txtOrderId.setText("#"+order.getOid());
        txtStatus.setText(order.getStatus().toUpperCase());
        txtDate.setText(DateFormatter.getDate(order.getMeettime(), "EEE, dd MMM yyyy, HH:mm"));

        String angka = Integer.toString(order.getAmount());
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        String rupiah = rupiahFormat.format(Double.parseDouble(angka));

        txtAmount.setText("Rp."+rupiah);

    }

}
