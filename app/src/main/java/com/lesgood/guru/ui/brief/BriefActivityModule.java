package com.lesgood.guru.ui.brief;



import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 3/6/17.
 */

@Module
public class BriefActivityModule {
    BriefActivity activity;

    public BriefActivityModule(BriefActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    BriefActivity provideBriefActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    BriefPresenter provideBriefPresenter(){
        return new BriefPresenter(activity);
    }
}
