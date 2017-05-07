package com.lesgood.guru.data.user;



import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.data.main.MainComponent;
import com.lesgood.guru.data.main.MainModule;
import com.lesgood.guru.ui.brief.BriefActivityComponent;
import com.lesgood.guru.ui.brief.BriefActivityModule;
import com.lesgood.guru.ui.edit_profile.EditProfileActivityComponent;
import com.lesgood.guru.ui.edit_profile.EditProfileActivityModule;
import com.lesgood.guru.ui.main.MainActivityComponent;
import com.lesgood.guru.ui.main.MainActivityModule;
import com.lesgood.guru.ui.setting.SettingActivityComponent;
import com.lesgood.guru.ui.setting.SettingActivityModule;

import dagger.Subcomponent;

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {

        MainActivityComponent plus(MainActivityModule activityModule);

        MainComponent plus(MainModule mainModule);

        EditProfileActivityComponent plus(EditProfileActivityModule activityModule);

        SettingActivityComponent plus(SettingActivityModule activityModule);

        BriefActivityComponent plus(BriefActivityModule activityModule);
}
