package com.lesgood.guru.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.home.HomeFragment;
import com.lesgood.guru.ui.order.OrderFragment;
import com.lesgood.guru.ui.profile.ProfileFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.navigation)
    BottomNavigationView navigation;

    @Inject
    MainPresenter presenter;

    @Inject
    User user;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = HomeFragment.newInstance();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = HomeFragment.newInstance();
                    break;
                case R.id.navigation_order:
                    fragment = OrderFragment.newInstance();
                    break;
                case R.id.navigation_profile:
                    fragment = ProfileFragment.newInstance();
                    break;
            }

            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
                return true;
            }

            return false;
        }

    };


    public static void startWithUser(BaseActivity activity, final User user) {
        Intent intent = new Intent(activity, MainActivity.class);
        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver,
                new IntentFilter("tokenReceiver"));

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = HomeFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

    }

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            if(token != null)
            {
                presenter.updateFCMToken(user.getUid(),token);
            }

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getUserComponent()
                .plus(new MainActivityModule(this))
                .inject(this);

        BaseApplication.get(this).createMainComponent(this);
    }
}
