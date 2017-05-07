package com.lesgood.guru.ui.order;


import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/27/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                OrderFragmentModule.class
        }
)
public interface OrderFragmentComponent {
    OrderFragment inject(OrderFragment fragment);
}
