package com.lesgood.guru.ui.login;



import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 2/27/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                LoginActivityModule.class
        }
)

public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity activity);
}
