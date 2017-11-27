package com.lesgood.guru.ui.map;

import com.lesgood.guru.base.annotation.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by sim-x on 11/27/17.
 */

@ActivityScope
@Subcomponent(
    modules = {
        MapActivityModule.class
    }
)
public interface MapActivityComponent {
  MapsActivity inject(MapsActivity activity);
}
