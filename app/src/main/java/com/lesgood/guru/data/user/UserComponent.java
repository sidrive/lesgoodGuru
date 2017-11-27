package com.lesgood.guru.data.user;



import com.lesgood.guru.base.annotation.UserScope;
import com.lesgood.guru.data.location.LocationComponent;
import com.lesgood.guru.data.location.LocationModule;
import com.lesgood.guru.data.main.MainComponent;
import com.lesgood.guru.data.main.MainModule;
import com.lesgood.guru.data.skill.SkillComponent;
import com.lesgood.guru.data.skill.SkillModule;
import com.lesgood.guru.data.verification.VerificationActivityComponent;
import com.lesgood.guru.data.verification.VerificationActivityModule;
import com.lesgood.guru.ui.add_location.AddLocationActivityComponent;
import com.lesgood.guru.ui.add_location.AddLocationActivityModule;
import com.lesgood.guru.ui.brief.BriefActivityComponent;
import com.lesgood.guru.ui.brief.BriefActivityModule;
import com.lesgood.guru.ui.edit_profile.EditProfileActivityComponent;
import com.lesgood.guru.ui.edit_profile.EditProfileActivityModule;
import com.lesgood.guru.ui.intro.IntroActivityComponent;
import com.lesgood.guru.ui.intro.IntroActivityModule;
import com.lesgood.guru.ui.main.MainActivityComponent;
import com.lesgood.guru.ui.main.MainActivityModule;
import com.lesgood.guru.ui.map.MapActivityComponent;
import com.lesgood.guru.ui.map.MapActivityModule;
import com.lesgood.guru.ui.payment_detail.PaymentDetailActivityComponent;
import com.lesgood.guru.ui.payment_detail.PaymentDetailActivityModule;
import com.lesgood.guru.ui.pengalaman.PengalamanActivityComponent;
import com.lesgood.guru.ui.pengalaman.PengalamanActivityModule;
import com.lesgood.guru.ui.prestasi.PrestasiActivityComponent;
import com.lesgood.guru.ui.prestasi.PrestasiActivityModule;
import com.lesgood.guru.ui.reviews.ReviewsActivityComponent;
import com.lesgood.guru.ui.reviews.ReviewsActivityModule;
import com.lesgood.guru.ui.setting.SettingActivityComponent;
import com.lesgood.guru.ui.setting.SettingActivityModule;
import com.lesgood.guru.ui.skill.SkillActivityComponent;
import com.lesgood.guru.ui.skill.SkillActivityModule;

import dagger.Subcomponent;

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {

        IntroActivityComponent plus(IntroActivityModule activityModule);

        MainActivityComponent plus(MainActivityModule activityModule);

        MainComponent plus(MainModule mainModule);

        EditProfileActivityComponent plus(EditProfileActivityModule activityModule);

        SettingActivityComponent plus(SettingActivityModule activityModule);

        BriefActivityComponent plus(BriefActivityModule activityModule);

        SkillActivityComponent plus(SkillActivityModule activityModule);

        PrestasiActivityComponent plus(PrestasiActivityModule activityModule);

        PengalamanActivityComponent plus(PengalamanActivityModule activityModule);

        SkillComponent plus(SkillModule module);

        LocationComponent plus(LocationModule locationModule);

        AddLocationActivityComponent plus(AddLocationActivityModule activityModule);

        PaymentDetailActivityComponent plus(PaymentDetailActivityModule activityModule);

        VerificationActivityComponent plus(VerificationActivityModule activityModule);

        ReviewsActivityComponent plus(ReviewsActivityModule activityModule);

        MapActivityComponent plus(MapActivityModule mapActivityModule);
}
