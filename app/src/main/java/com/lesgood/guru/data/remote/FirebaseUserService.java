package com.lesgood.guru.data.remote;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;


public class FirebaseUserService {
    private Application application;

    private FirebaseAuth firebaseAuth;

    // for google
    private GoogleApiClient googleApiClient;
    AuthCredential credential;
    // for facebook
    private CallbackManager callbackManager;

    public FirebaseUserService(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> getUserWithEmail(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> createUserWithEmail(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public Task<ProviderQueryResult> checkEmail(String email){
        return firebaseAuth.fetchProvidersForEmail(email);
    }


    public Intent getUserWithGoogle(BaseActivity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))

                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, connectionResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    }

    public Task<AuthResult> getAuthWithGoogle(final BaseActivity activity, GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        googleApiClient.connect();
        return firebaseAuth.signInWithCredential(credential);
    }

    public CallbackManager getUserWithFacebook() {
        FacebookSdk.sdkInitialize(application);
        callbackManager = CallbackManager.Factory.create();
        return callbackManager;
    }

    public Task<AuthResult> getAuthWithFacebook(AccessToken token) {
        credential = FacebookAuthProvider.getCredential(token.getToken());
        return firebaseAuth.signInWithCredential(credential);
    }

    public void logOut(String provider) {
        Log.e("logOut", "FirebaseUserService  " + provider);
        if(provider.equals("facebook.com")) {
            Log.e("logOut", "FirebaseUserService : " + "singout with facebook");
            FacebookSdk.sdkInitialize(application);
            LoginManager.getInstance().logOut();
        }else if (provider.equals("firebase")){
            firebaseAuth.signOut();
            Log.e("logOut", "googleApiClient " + googleApiClient);
            if (googleApiClient!=null){
                if(googleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        status -> {
                            if (status.isSuccess()) {
                                Log.d("googlesignout", "user logout");
                                Auth.CredentialsApi.disableAutoSignIn(googleApiClient);

                            }
                        });
                }
            }
          Log.e("logOut", "FirebaseUserService : " + "singout with firebase");
        }
        else if(provider.equals("google.com")) {
            Log.e("logOut", "FirebaseUserService : " + "singout with google");
            if (googleApiClient != null){
                googleApiClient.connect();
                googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                        FirebaseAuth.getInstance().signOut();
                        if(googleApiClient.isConnected()) {
                            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                                status -> {
                                    if (status.isSuccess()) {
                                        Log.d("googlesignout", "user logout");
                                        Auth.CredentialsApi.disableAutoSignIn(googleApiClient);

                                    }
                                });
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d("googlesignout", "Google API Client Connection Suspended");
                    }
                });
            }
        }
    }


    public void deleteUser(String uid) {

    }

}
