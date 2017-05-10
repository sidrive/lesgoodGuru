package com.lesgood.guru.data.user;



import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.CategoryService;
import com.lesgood.guru.data.remote.LocationService;
import com.lesgood.guru.data.remote.OrderService;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {
    User user;

    public UserModule(User user) {
        this.user = user;
    }

    @Provides
    @UserScope
    User provideUser() {
        return user;
    }

    @Provides
    @UserScope
    CategoryService provideCategoryService(){
        return new CategoryService();
    }

    @Provides
    @UserScope
    OrderService provideOrderService(){
        return new OrderService();
    }

    @UserScope
    @Provides
    LocationService locationService(){
        return new LocationService();
    }

}
