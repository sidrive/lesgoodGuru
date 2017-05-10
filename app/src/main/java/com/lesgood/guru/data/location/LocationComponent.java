package com.lesgood.guru.data.location;


import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.ui.add_location.AddLocationActivityComponent;
import com.lesgood.guru.ui.add_location.AddLocationActivityModule;

import dagger.Subcomponent;

/**
 * Created by Agus on 3/14/17.
 */

@UserScope
@Subcomponent(
        modules = {
                LocationModule.class
        }
)
public interface LocationComponent {

}
