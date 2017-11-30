package com.lesgood.guru.ui.wallet;

import com.lesgood.guru.base.annotation.ActivityScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by sim-x on 11/30/17.
 */
@Module
public class WalletActivityModule {
  WalletActivity activity;

  public WalletActivityModule(WalletActivity activity) {
    this.activity = activity;
  }
  @ActivityScope
  @Provides
  WalletActivity provideWalletActivity(){
    return activity;
  }
  @ActivityScope
  @Provides
  WalletPresenter provideWalletPresenter(UserService userService,User user){
    return new WalletPresenter(activity,userService,user);
  }
}
