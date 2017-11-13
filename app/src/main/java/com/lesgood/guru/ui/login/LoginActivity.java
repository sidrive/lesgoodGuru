package com.lesgood.guru.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.edit_profile.EditProfileActivity;
import com.lesgood.guru.ui.intro.IntroActivity;
import com.lesgood.guru.ui.main.MainActivity;


import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    public static final int REQUEST_SIGN_GOOGLE = 9001;

    @BindString(R.string.error_field_required)
    String errorRequired;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Inject
    LoginPresenter presenter;

    private CallbackManager callbackManager;

    boolean isLoginMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new LoginActivityModule(this))
                .inject(this);
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

    @OnClick(R.id.btn_login_with_facebook)
    public void onBtnLoginWithFacebook() {
        callbackManager = presenter.loginWithFacebook();
    }

    @OnClick(R.id.btn_login_with_google)
    public void loginwithgoogle(){
        Intent intent = presenter.loginWithGoogle();
        startActivityForResult(intent, REQUEST_SIGN_GOOGLE);
    }


    public void showLoginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showLoginSuccess(User user) {
        MainActivity.startWithUser(this, user);
        finish();
    }

    public void showIntroActivity(User user){
        IntroActivity.startWithUser(this, user);
        finish();
    }

    public void showRegisterUser(User user){
        EditProfileActivity.startWithUser(this, user, true);
        finish();

    }

    public void showLoading(boolean show){
        if (show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }

    public void showLoginMode(){
        isLoginMode = true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            // google
            if(requestCode == REQUEST_SIGN_GOOGLE) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                //presenter.cekEmail(result);
                showLoading(true);
                presenter.getAuthWithGoogle(result);
            }
            // facebook
            else if(requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }if (resultCode == RESULT_CANCELED){
            presenter.revoke();
        }

    }


    private boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}

