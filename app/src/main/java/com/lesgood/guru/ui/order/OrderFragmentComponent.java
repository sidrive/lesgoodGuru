package com.lesgood.guru.ui.order;



import com.lesgood.guru.base.annotation.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 3/2/17.
 */

@FragmentScope
@Subcomponent(
        modules = {
                OrderFragmentModule.class
        }
)
public interface OrderFragmentComponent {
    OrderFragment inject(OrderFragment fragment);
}
