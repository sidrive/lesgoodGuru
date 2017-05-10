package com.lesgood.guru.data.skill;

import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.data.model.Skill;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/10/17.
 */

@Module
public class SkillModule {

    Skill skill;

    public SkillModule(Skill skill){
        this.skill = skill;
    }

    @UserScope
    @Provides
    Skill provideSkill(){
        return skill;
    }
}
