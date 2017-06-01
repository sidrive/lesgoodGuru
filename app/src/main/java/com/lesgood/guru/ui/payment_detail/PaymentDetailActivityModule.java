package com.lesgood.guru.ui.payment_detail;


import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 3/2/17.
 */

@Module
public class PaymentDetailActivityModule {
    PaymentDetailActivity activity;

    public PaymentDetailActivityModule(PaymentDetailActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    PaymentDetailActivity providePaymentDetailActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    PaymentDetailPresenter providePaymentDetailPresenter(User user, UserService userService){
        return new PaymentDetailPresenter(activity,user, userService);
    }
}
