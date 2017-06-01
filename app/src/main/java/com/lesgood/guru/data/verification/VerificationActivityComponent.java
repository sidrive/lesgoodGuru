package com.lesgood.guru.data.verification;

import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/31/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                VerificationActivityModule.class
        }
)
public interface VerificationActivityComponent {
    VerificationActivity inject(VerificationActivity activity);
}
