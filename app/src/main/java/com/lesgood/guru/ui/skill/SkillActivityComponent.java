package com.lesgood.guru.ui.skill;

import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/10/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                SkillActivityModule.class
        }
)
public interface SkillActivityComponent {
    SkillActivity inject(SkillActivity activity);
}
