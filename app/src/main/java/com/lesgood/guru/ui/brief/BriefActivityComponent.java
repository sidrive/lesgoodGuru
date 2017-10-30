package com.lesgood.guru.ui.brief;




import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 3/6/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                BriefActivityModule.class
        }
)
public interface BriefActivityComponent {
    BriefActivity inject(BriefActivity activity);
}
