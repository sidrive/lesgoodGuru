package com.lesgood.guru.ui.skill;

import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/10/17.
 */
@Module
public class SkillActivityModule {
    SkillActivity activity;

    public SkillActivityModule(SkillActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    SkillActivity provideSkillActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    SkillPresenter provideSkillPresenter(UserService userService, User user){
        return new SkillPresenter(activity, userService, user);
    }

    @ActivityScope
    @Provides
    SkillAdapter provideSkillAdapter(){
        return new SkillAdapter(activity);
    }
}
