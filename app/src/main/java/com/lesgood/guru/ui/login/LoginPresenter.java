package com.lesgood.guru.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.FirebaseUserService;
import com.lesgood.guru.data.remote.UserService;


import com.lesgood.guru.util.AppUtils;
import java.util.Arrays;

/**
 * Created by Agus on 2/27/17.
 */

public class LoginPresenter implements BasePresenter {
    LoginActivity activity;
    UserService userService;
    FirebaseUserService firebaseUserService;

    public LoginPresenter(LoginActivity activity, UserService userService, FirebaseUserService firebaseUserService){
        this.activity = activity;
        this.userService = userService;
        this.firebaseUserService = firebaseUserService;

    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }

    protected void loginWithEmail(final String email, final String password) {
        activity.showLoading(true);
        firebaseUserService.getUserWithEmail(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            activity.showLoading(false);
                            for(UserInfo profile : task.getResult().getUser().getProviderData()) {
                                String providerId = profile.getProviderId();
                                String uid = profile.getUid();
                                String name = profile.getDisplayName();
                                String email = profile.getEmail();
                                Uri photoUri = profile.getPhotoUrl();
                                Log.d("fisache", providerId + " " + uid + " " + name + " " + email + " " + photoUri);
                            }
                            processLogin(task.getResult().getUser(), task.getResult().getUser().getProviderData().get(1));
                        } else {
                            activity.showLoading(false);
                            User user = new User();
                            user.setEmail(email);
                            user.setProvider("password");
                            activity.showRegisterUser(user);
                        }
                    }
                });

    }

    protected Intent loginWithGoogle() {
        return firebaseUserService.getUserWithGoogle(activity);
    }

    protected void getAuthWithGoogle(GoogleSignInResult result) {
        if(result.isSuccess()) {
            final GoogleSignInAccount acct = result.getSignInAccount();
            firebaseUserService.getAuthWithGoogle(activity, acct)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            activity.showLoading(false);
                            for(UserInfo profile : task.getResult().getUser().getProviderData()) {
                                String providerId = profile.getProviderId();
                                String uid = profile.getUid();
                                String name = profile.getDisplayName();
                                String email = profile.getEmail();
                                Uri photoUri = profile.getPhotoUrl();
                                Log.d("users  = ", providerId + " " + uid + " " + name + " " + email + " " + photoUri);
                            }
                            Log.e("getAuthWithGoogle", "LoginPresenter" + task.getResult().getUser().getProviderData().toString());
                            processLogin(task.getResult().getUser(), task.getResult().getUser().getProviderData().get(1));
                        } else {
                            activity.showLoading(false);
                            activity.showLoginFail("Gagal Masuk");
                        }
                    }).addOnFailureListener(e -> {activity.showLoginFail(e.getMessage());});
        } else {
            activity.showLoginFail("Gagal Masuk");
        }
    }

    protected CallbackManager loginWithFacebook() {
        CallbackManager callbackManager = firebaseUserService.getUserWithFacebook();
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getAuthWithFacebook(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        activity.showLoginFail("Dibatalkan");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        activity.showLoginFail("Gagal Masuk");
                    }
                });
        return callbackManager;
    }

    protected void getAuthWithFacebook(final AccessToken accessToken) {
        activity.showLoading(true);
        firebaseUserService.getAuthWithFacebook(accessToken)
                .addOnCompleteListener(activity, task -> {
                    if(task.isSuccessful()) {
                        activity.showLoading(false);
                        for(UserInfo profile : task.getResult().getUser().getProviderData()) {
                            String providerId = profile.getProviderId();
                            String uid = profile.getUid();
                            String name = profile.getDisplayName();
                            String email = profile.getEmail();
                            Uri photoUri = profile.getPhotoUrl();
                            Log.d("fisache", providerId + " " + uid + " " + name + " " + email + " " + photoUri);
                        }
                        //processLogin(task.getResult().getUser(), task.getResult().getUser().getProviderData().get(1));
                    } else {
                        activity.showLoading(false);
                        activity.showLoginFail("Oops, email sudah digunakan");
                    }
                });
    }

    private void processLogin(final FirebaseUser firebaseUser, UserInfo userInfo) {
        final User user = User.newInstance(firebaseUser, userInfo);
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
                            activity.showRegisterUser(user);

                        } else {
                            if (remoteUser.isAcceptTOS()) activity.showLoginSuccess(remoteUser);
                            else activity.showIntroActivity(remoteUser);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        activity.showLoginFail("Gagal Masuk");
                    }
                }
        );
    }
    public  void cekEmail(GoogleSignInResult result){
        firebaseUserService.checkEmail(result.getSignInAccount().getEmail()).addOnSuccessListener(providerQueryResult -> {
            if (providerQueryResult.getProviders().isEmpty()){
                getAuthWithGoogle(result);
            }else{
                activity.showLoading(false);
                activity.showLoginFail("Email sudah digunakan oleh provider lain (Facebook atau Google)");
                firebaseUserService.revokeTokenGoogle(activity);
            }
        }).addOnFailureListener(e -> {
            activity.showLoading(false);
            activity.showLoginFail("Email sudah digunakan ");
        });
    }
    public void emailIsRegistered(final String email) {
        firebaseUserService.checkEmail(email).addOnCompleteListener(task -> {
            if (task.getResult().getProviders().size() > 0) {
                if (!task.getResult().getProviders().get(0).equals("password")){
                    activity.showLoginFail("Email sudah digunakan oleh provider lain (Facebook atau Google)");
                }else{
                    activity.showLoginMode();
                }
            }else{
                User user = new User();
                user.setEmail(email);
                user.setProvider("password");
                activity.showRegisterUser(user);
            }

        });
    }
    public void revoke(){
        firebaseUserService.revokeTokenGoogle(activity);
    }
}
