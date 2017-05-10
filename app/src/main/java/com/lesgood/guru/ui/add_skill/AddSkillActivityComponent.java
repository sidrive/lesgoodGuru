package com.lesgood.guru.ui.add_skill;

import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/9/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                AddSkillActivityModule.class
        }
)
public interface AddSkillActivityComponent {
    AddSkillActivity inject(AddSkillActivity activity);
}
