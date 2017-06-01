package com.lesgood.guru.ui.payment_detail;


import com.lesgood.guru.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 3/2/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                PaymentDetailActivityModule.class
        }
)
public interface PaymentDetailActivityComponent {
    PaymentDetailActivity inject(PaymentDetailActivity activity);
}
