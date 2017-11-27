package com.lesgood.guru.ui.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.lesgood.guru.BuildConfig;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;

import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.FirebaseInstanceIDService;
import com.lesgood.guru.ui.home.HomeFragment;
import com.lesgood.guru.ui.order.OrderFragment;
import com.lesgood.guru.ui.profile.ProfileFragment;
import com.lesgood.guru.ui.update_information.UpdateInformationActivity;

import com.lesgood.guru.util.AppUtils;
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

    public FirebaseRemoteConfig mFirebaseRemoteConfig;

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


    public static void startWithUser(Activity activity, final User user) {
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

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        fetchWelcome();
        String token = FirebaseInstanceId.getInstance().getToken();
        presenter.updateFCMToken(user.getUid(),token);

    }

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            if(token != null).
            {
                presenter.updateFCMToken(user.getUid(),token);
            }

        }
    };

    private void fetchWelcome() {

        long cacheExpiration = 0; // 1 hour in seconds.
        // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
        // the server.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating that any previously
        // fetched and cached config would be considered expired because it would have been fetched
        // more than cacheExpiration seconds ago. Thus the next fetch would go to the server unless
        // throttling is in progress. The default expiration duration is 43200 (12 hours).
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("REMOTECONFIG", "FETCH SUCCEEDED");

                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {


                        }

                        setConfig();

                    }
                });
        // [END fetch_config_with_callback]
    }


    private void setConfig(){
        int latestVersion = Integer.parseInt(mFirebaseRemoteConfig.getString("latest_version_code"));

        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            int currVersion = info.versionCode;

            if (currVersion<latestVersion){
                Intent intent = new Intent(this, UpdateInformationActivity.class);
                startActivity(intent);
            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
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
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getUserComponent()
                .plus(new MainActivityModule(this))
                .inject(this);

        BaseApplication.get(this).createMainComponent(this);
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        AppUtils.showToas(this,"Double tap to exit");
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }
}
