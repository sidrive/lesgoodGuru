package com.lesgood.guru.data.skill;

import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.ui.add_skill.AddSkillActivity;
import com.lesgood.guru.ui.add_skill.AddSkillActivityComponent;
import com.lesgood.guru.ui.add_skill.AddSkillActivityModule;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/10/17.
 */

@UserScope
@Subcomponent(
        modules = {
                SkillModule.class
        }
)
public interface SkillComponent {
    AddSkillActivityComponent plus(AddSkillActivityModule activityModule);
}
