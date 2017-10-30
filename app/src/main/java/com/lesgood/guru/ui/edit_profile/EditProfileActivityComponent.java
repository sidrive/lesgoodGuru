package com.lesgood.guru.ui.edit_profile;


import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/20/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                EditProfileActivityModule.class
        }
)
public interface EditProfileActivityComponent {
    EditProfileActivity inject(EditProfileActivity activity);
}
