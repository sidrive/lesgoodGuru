package com.lesgood.guru.ui.order;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BaseFragment;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.ui.order_detail.OrderDetailActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agus on 2/22/17.
 */

public class PlaceholderFragment extends BaseFragment {

    private static String TAG = "OrderFrag";

    @Bind(R.id.rv_order)
    RecyclerView rvOrder;

    @Inject
    PlaceHolderFragmentPresenter presenter;

    @Inject
    User user;

    @Inject
    MainActivity activity;

    OrderAdapter orderAdapter;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent() {
        BaseApplication.get(getActivity())
                .getMainComponent()
                .plus(new PlaceholderFragmentModule(this))
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        orderAdapter.clearList();

        Log.d(TAG, "onResume");

        int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        if (sectionNumber == 1){
            presenter.getOrders("waiting");
        }

        if (sectionNumber == 2){
            presenter.getOrders("complete");
        }
        if (sectionNumber == 3){
            presenter.getOrders("oper order");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        orderAdapter.clearList();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, rootView);

        orderAdapter = new OrderAdapter(this);

        int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        showOrders();

        return rootView;
    }

    public void showAddedOrder(Order order) {
        orderAdapter.onOrderAdded(order);
        if (order.getStatusPayment() != null){
            if(order.getStatus().equalsIgnoreCase("SUCCESS")|| order.getStatusPayment().equalsIgnoreCase("SUCCESS")){
                dialogBox();
            }
        }
        if(order.getStatus().equalsIgnoreCase("SUCCESS")){
            dialogBox();
        }
    }

    public void showChangedOrder(Order order) {
        orderAdapter.onOrderChanged(order);
    }

    public void showOrders() {
        rvOrder.setAdapter(orderAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvOrder.setLayoutManager(linearLayoutManager);

    }

    public void showDetailOrder(Order order){
        OrderDetailActivity.startWithOrder(activity, order);
    }

    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Murid Telah Menyelesaikan Pembayaran, Silahkan menghubungi murid untuk memperkenalkan diri");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
