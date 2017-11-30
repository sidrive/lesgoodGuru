package com.lesgood.guru.ui.wallet;

import com.lesgood.guru.base.annotation.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by sim-x on 11/30/17.
 */

@ActivityScope
@Subcomponent(modules = {
    WalletActivityModule.class
})
public interface WalletActivityComponent {
  WalletActivity inject(WalletActivity activity);
}
