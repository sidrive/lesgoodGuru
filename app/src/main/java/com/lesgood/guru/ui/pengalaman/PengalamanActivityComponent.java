package com.lesgood.guru.ui.pengalaman;

import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 6/1/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                PengalamanActivityModule.class
        }
)
public interface PengalamanActivityComponent {
    PengalamanActivity inject(PengalamanActivity activity);
}
