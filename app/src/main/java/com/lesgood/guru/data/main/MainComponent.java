package com.lesgood.guru.data.main;

import com.lesgood.guru.base.annotation.MainScope;
import com.lesgood.guru.ui.home.HomeFragmentComponent;
import com.lesgood.guru.ui.home.HomeFragmentModule;
import com.lesgood.guru.ui.order.OrderFragmentComponent;
import com.lesgood.guru.ui.order.OrderFragmentModule;
import com.lesgood.guru.ui.profile.ProfileFragmentComponent;
import com.lesgood.guru.ui.profile.ProfileFragmentModule;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/20/17.
 */

@MainScope
@Subcomponent(
        modules = {
                MainModule.class
        }
)
public interface MainComponent {

        ProfileFragmentComponent plus(ProfileFragmentModule fragmentModule);

        HomeFragmentComponent plus(HomeFragmentModule fragmentModule);

        OrderFragmentComponent plus(OrderFragmentModule fragmentModule);
}
