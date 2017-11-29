package com.lesgood.guru.ui.splash;

import android.support.annotation.NonNull;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.OrderService;
import com.lesgood.guru.data.remote.UserService;
import javax.inject.Inject;


public class SplashPresenter implements BasePresenter {

    SplashActivity activity;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private UserService userService;


    public SplashPresenter(SplashActivity activity, UserService userService) {
        this.activity = activity;
        this.userService = userService;
        this.firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void unsubscribe() {
        if(authListener != null) {
            firebaseAuth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public void subscribe() {
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user == null) {
                    activity.showLoginActivity();
                } else {
                    processLogin(user);
                }
            }
        };

        firebaseAuth.addAuthStateListener(authListener);
    }

    private void processLogin(final FirebaseUser fuser) {
        final User user = User.newInstance(fuser, fuser);
        userService.getUser(user.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User remoteUser = dataSnapshot.getValue(User.class);

                        if(remoteUser == null || remoteUser.getFull_name() == null || remoteUser.getEmail() == null) {
                            if (remoteUser != null){
                                if (remoteUser.getFull_name() != null) user.setFull_name(remoteUser.getFull_name());
                                if (remoteUser.getEmail() != null) user.setEmail(remoteUser.getEmail());
                                if (remoteUser.getPhone() != null) user.setPhone(remoteUser.getPhone());
                            }

                            activity.showRegisterActivity(user);
                        } else {
                            /*if (remoteUser.isVerified() == true) {*/ activity.showMainActivity(remoteUser);
                            sendUser(user.getUid());
                           /* }
                            else activity.showVerificationActivity(remoteUser);*/

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        activity.showLoginActivity();
                    }
                }
        );
    }

    public String sendUser(String uid){
        return uid;
    }

    public void updateFCMToken(String token){
        String uid = "hQg3kf6isfXDNsD3JuphFt79ffg2";
        userService.updateUserToken(uid, token);
    }

}
