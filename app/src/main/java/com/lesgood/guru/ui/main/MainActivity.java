package com.lesgood.guru.ui.main;

import android.Manifest.permission;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


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
import com.lesgood.guru.data.verification.VerificationActivity;
import com.lesgood.guru.ui.home.HomeFragment;
import com.lesgood.guru.ui.order.OrderFragment;
import com.lesgood.guru.ui.order_detail.OrderDetailActivity;
import com.lesgood.guru.ui.profile.ProfileFragment;
import com.lesgood.guru.ui.update_information.UpdateInformationActivity;

import com.lesgood.guru.util.AppUtils;
import com.lesgood.guru.util.Utils;
import java.util.List;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity  implements EasyPermissions.PermissionCallbacks{

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.navigation)
    BottomNavigationView navigation;

    @Inject
    MainPresenter presenter;

    @Inject
    User user;

    public FirebaseRemoteConfig mFirebaseRemoteConfig;

    private static final int RC_ALL_PERMISSION= 111;

    private static final String[] PERMISION =
        {permission.ACCESS_FINE_LOCATION,
            permission.READ_CONTACTS,
            permission.READ_EXTERNAL_STORAGE,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.CAMERA
        };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

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
            };


    public static void startWithUser(Activity activity, final User user) {
        Intent intent = new Intent(activity, MainActivity.class);

        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }
    public static void startWithUserParam(Activity activity, final User user,String param) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("param",param);
        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver,
                new IntentFilter("tokenReceiver"));
        if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.M) {
           requestPermissionForMvers();
        }
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        fetchWelcome();
        String token = FirebaseInstanceId.getInstance().getToken();
        presenter.updateFCMToken(user.getUid(),token);
        Bundle extra = getIntent().getExtras();
        if (extra!=null){
            String orderID = extra.getString("param");
            Fragment fragment = OrderFragment.newInstanceParam(orderID);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }else {
            Fragment fragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        if (!user.isVerified()){
            Utils.showDialog(this,"Akun anda belum terverifikasi atau dokumen verifikasi belum lengkap, lengkapi data sekarang ?",listener);
        }
    }
    public DialogInterface.OnClickListener listener = (dialog, which) -> {
        dialog.dismiss();
        openVerification();
    };
    private void requestPermissionForMvers() {
        if (
            ActivityCompat.checkSelfPermission(this, PERMISION[0]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, PERMISION[1]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, PERMISION[2]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, PERMISION[3]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, PERMISION[4]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[2])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[3])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[4])) {
            } else {
                ActivityCompat.requestPermissions(this,PERMISION,RC_ALL_PERMISSION);
            }
        }
    }

    public DialogInterface.OnClickListener positifBtn = (dialog, which) -> {
      dialog.dismiss();
      ActivityCompat.requestPermissions(this,PERMISION,RC_ALL_PERMISSION);
    };

    public DialogInterface.OnClickListener negativBtn = (dialog, which) -> {
        dialog.dismiss();
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_ALL_PERMISSION){
            boolean allGrant = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allGrant = true;
                } else {
                    allGrant = false;
                }
            }
            if (allGrant) {
               AppUtils.showToast(this,"GRANTED");
            } else {
                AppUtils.showToast(this,"DENIED");
            }

        }

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
   /* @Override
    public void onBackPressed() {
        System.exit(1);
        *//*if (doubleBackToExitPressedOnce) {

            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        AppUtils.showToast(this,"Double tap to exit");
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = true, 2000);*//*
        return;
    }*/

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    public void openVerification() {
        VerificationActivity.startWithUser(this,user);
    }
}
