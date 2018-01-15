package com.lesgood.guru.ui.order;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.google.android.gms.location.DetectedActivity;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BaseFragment;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.main.MainActivity;

import com.lesgood.guru.ui.order_detail.OrderDetailActivity;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 2/22/17.
 */

public class OrderFragment extends BaseFragment {

    @BindColor(R.color.colorShadow2)
    int colorAccentDark;

    @BindColor(R.color.colorBlack)
    int colorBlack;
    ;

    @Bind(R.id.container)
    ViewPager mViewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Inject
    User user;


    @Inject
    OrderPresenter presenter;

    @Inject
    MainActivity activity;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    public static OrderFragment newInstanceParam(String param) {
        Bundle args = new Bundle();
        args.putString("param",param);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static OrderFragment newInstanceParam() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }
    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupFragmentComponent() {
        BaseApplication.get(getActivity())
                .getMainComponent()
                .plus(new OrderFragmentModule(this))
                .inject(this);
    }
    String oid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle ex = getArguments();
        if (ex!=null){
            oid = ex.getString("param");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
        if (oid!=null){
            presenter.openOrderdetail(oid);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //presenter.subscribe();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Orders");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

    }


    public void openDetailOrder(Order order) {
        OrderDetailActivity.startWithOrder(activity,order);
    }
}
