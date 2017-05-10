package com.lesgood.guru.ui.add_skill;

import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.CategoryService;
import com.lesgood.guru.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/9/17.
 */
@Module
public class AddSkillActivityModule {
    AddSkillActivity activity;

    public AddSkillActivityModule(AddSkillActivity activity){
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    AddSkillActivity provideAddSkillActivity(){
        return activity;
    }

    @Provides
    @ActivityScope
    AddSkillPresenter provideAddSkillPresenter(UserService userService, CategoryService categoryService, User user){
        return new AddSkillPresenter(activity, userService, categoryService, user);
    }
}
