package com.lesgood.guru.ui.add_location;


import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 3/3/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                AddLocationActivityModule.class
        }
)
public interface AddLocationActivityComponent {
    AddLocationActivity inject(AddLocationActivity activity);
}
