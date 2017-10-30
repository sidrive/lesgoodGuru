package com.lesgood.guru.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.home.HomeFragmentModule;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.ui.slide_fragment.KebijakanSlide;
import com.lesgood.guru.ui.slide_fragment.PanduanSlide;
import com.lesgood.guru.ui.slide_fragment.PerjanjianSlide;

import javax.inject.Inject;

import agency.tango.materialintroscreen.MaterialIntroActivity;

/**
 * Created by Agus on 9/23/17.
 */

public class IntroActivity extends MaterialIntroActivity {

    @Inject
    User user;

    @Inject
    IntroPresenter presenter;

    public static void startWithUser(BaseActivity activity, final User user) {
        Intent intent = new Intent(activity, IntroActivity.class);
        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentComponent();

        addSlide(new KebijakanSlide());
        addSlide(new PerjanjianSlide());
        addSlide(new PanduanSlide());


    }

    void setupFragmentComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .plus(new IntroActivityModule(this))
                .inject(this);
    }

    public void showMainActivity(){
        MainActivity.startWithUser(this, user);
        finish();
    }

    @Override
    public void onFinish() {
        super.onFinish();
        presenter.save(user.getUid(), true);
    }

}
