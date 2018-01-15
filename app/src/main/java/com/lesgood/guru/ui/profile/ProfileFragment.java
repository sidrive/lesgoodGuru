package com.lesgood.guru.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BaseFragment;
import com.lesgood.guru.base.config.DefaultConfig;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.verification.VerificationActivity;
import com.lesgood.guru.ui.add_location.AddLocationActivity;
import com.lesgood.guru.ui.brief.BriefActivity;
import com.lesgood.guru.ui.edit_profile.EditProfileActivity;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.ui.pengalaman.PengalamanActivity;
import com.lesgood.guru.ui.prestasi.PrestasiActivity;
import com.lesgood.guru.ui.reviews.ReviewsActivity;
import com.lesgood.guru.ui.setting.SettingActivity;
import com.lesgood.guru.ui.skill.SkillActivity;
import com.lesgood.guru.ui.wallet.WalletActivity;
import com.lesgood.guru.util.Const;
import com.lesgood.guru.util.TypefacedTextView;
import com.lesgood.guru.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import javax.inject.Inject;

/**
 * Created by Agus on 2/22/17.
 */

public class ProfileFragment extends BaseFragment {

  private static String TAG = "ProfileFragment";
  private static int REQUEST_CODE_ADD_BRIEF = 1054;
  private static int REQUEST_CODE_SKIL = 1059;
  private static String TITILE = "Profil";
  @Bind(R.id.txt_name)
  TextView txtName;

  @Bind(R.id.txt_about)
  TextView txtAbout;

  @Bind(R.id.txt_skills)
  TextView txtSkills;

  @Bind(R.id.txt_location)
  TextView txtLocation;

  @Bind(R.id.img_avatar)
  CircleImageView imgAvatar;

  @Bind(R.id.img_bg_avatar)
  ImageView imgBgAvatar;

  @Bind(R.id.txt_price)
  TextView txtPrice;

  @Bind(R.id.txt_verified)
  TextView txtVerified;

  @Bind(R.id.txt_pendidikan)
  TextView txtPendidikan;

  @Bind(R.id.rating_bar)
  RatingBar rating;

  @Bind(R.id.txt_rating)
  TextView totalrating;

  @Inject
  ProfilePresenter presenter;

  @Inject
  User user;

  @Inject
  MainActivity activity;

  String userAbout = "";

  Location location;
  @Bind(R.id.txt_saldo)
  TypefacedTextView txtSaldo;
  @Bind(R.id.lin_prestasi)
  LinearLayout linPrestasi;
  @Bind(R.id.txt_pengalaman)
  TypefacedTextView txtPengalaman;
  @Bind(R.id.lin_pengalaman)
  LinearLayout linPengalaman;
  @Bind(R.id.lin_verified)
  LinearLayout linVerified;
  @Bind(R.id.lin_location)
  LinearLayout linLocation;

  public static ProfileFragment newInstance() {
    return new ProfileFragment();
  }

  public ProfileFragment() {
    // Required empty public constructor
  }

  @Override
  protected void setupFragmentComponent() {
    BaseApplication.get(getActivity())
        .getMainComponent()
        .plus(new ProfileFragmentModule(this))
        .inject(this);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.subscribe();
    init();
  }

  @Override
  public void onStop() {
    super.onStop();
    presenter.unsubscribe();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.setting, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.menu_setting) {
      startActivity(new Intent(activity, SettingActivity.class));
    }

    return super.onOptionsItemSelected(item);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    //returning our layout file
    //change R.layout.yourlayoutfilename for each of your fragments
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    ButterKnife.bind(this, view);
    getActivity().setTitle(TITILE);
    location = new Location(user.getUid());
    return view;
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_ADD_BRIEF) {
      if (resultCode == RESULT_OK) {
        String brief = data.getStringExtra(Const.EXSTRA_BRIEF);
        if (brief != null) {
          initAbout(brief);
          presenter.updateUserAbout(user.getUid(), brief);
        }
      }
    }

    if (requestCode == REQUEST_CODE_SKIL) {
      if (resultCode == RESULT_OK) {
        int total = data.getIntExtra(Const.EXTRA_TOTAL_SKILL, 0);
        int startForm = data.getIntExtra(Const.EXTRA_START_FROM, 0);

        if (startForm > 0) {
          BaseApplication.get(activity).createUserComponent(user);
          initPrice(startForm);
          presenter.updateUserPrice(user.getUid(), startForm);
        }
        user.setTotalSkill(total);
        setTotalSkillUser(total);
        BaseApplication.get(activity).createUserComponent(user);
      }
    }
  }

  public void setTotalSkillUser(int total) {
    txtSkills.setText(total + " Kemampuan mangajar");
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //you can set the title for your toolbar here for different fragments different titles
  }


  private void init() {
    txtName.setText(user.getFull_name());
    txtSaldo.setText("Saldo : "+Utils.getRupiah(user.getSaldo()));
    float ratings = user.getReview() / 10;
    totalrating.setText(String.valueOf(ratings));
    rating.setRating(ratings);
    if (user.getPhoto_url() != null) {
      if (!user.getPhoto_url().equalsIgnoreCase("NOT")) {
        Glide.with(this)
            .load(user.getPhoto_url()).listener(new RequestListener<String, GlideDrawable>() {
          @Override
          public boolean onException(Exception e, String model, Target<GlideDrawable> target,
              boolean isFirstResource) {
            Log.e("IMAGE_EXCEPTION", "Exception " + e.toString());
            return false;
          }

          @Override
          public boolean onResourceReady(GlideDrawable resource, String model,
              Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            Log.d("smtime img's not loaded", "n dis tex's not di");
            return false;
          }
        })
            .placeholder(R.color.colorSoft)
            .dontAnimate()
            .into(imgBgAvatar);

        Glide.with(this)
            .load(user.getPhoto_url())
            .placeholder(R.color.colorSoft)
            .dontAnimate()
            .into(imgAvatar);
      }
    }
    if (user.getTotalSkill() > 0) {
      txtSkills.setText(user.getTotalSkill() + " Kemampuan mangajar");
    }

    if (user.getFullAddress() != null) {
      txtLocation.setText(user.getFullAddress());
    }

    if (user.getPendidikan() != null) {
      txtPendidikan.setText(user.getPendidikan());
    }

    if (user.getVerified()!=null && user.getVerified()) {
      txtVerified.setText("Status : Terverifikasi");
    }

    txtPrice.setText(Utils.getRupiah(user.getStartFrom()) + "/per 100 menit");
  }


  public void initAbout(String content) {
    this.userAbout = content;
    txtAbout.setText(Html.fromHtml(content));
  }

  public void initLocation(Location location) {
    this.location = location;
  }

  public void initPrice(int price) {
    this.user.setStartFrom(price);
    txtPrice.setText(Utils.getRupiah(price) + " /100 menit");
  }

  @OnClick(R.id.btn_edit_profile)
  void startEditProfile() {
    EditProfileActivity.startWithUser(activity, user, false);
  }

  @OnClick(R.id.btn_edit_about)
  void showEditAbout() {
    Intent intent = new Intent(activity, BriefActivity.class);
    intent.putExtra("brief", userAbout);
    startActivityForResult(intent, REQUEST_CODE_ADD_BRIEF);
  }

  @OnClick(R.id.lin_skill)
  void showSkills() {
    startActivityForResult(new Intent(activity, SkillActivity.class), REQUEST_CODE_SKIL);
  }

  @OnClick(R.id.lin_location)
  void showSetLocation() {
    DefaultConfig defaultConfig = new DefaultConfig(getContext());
    defaultConfig.setApiUrl(Const.BASE_URL_MAP);
    AddLocationActivity.startWithUser(activity);
  }

  @OnClick(R.id.lin_saldo)
  void showSaldo() {
    //startActivity(new Intent(activity,WalletActivity.class));
    WalletActivity.start(activity, user.getUid());
  }

  @OnClick(R.id.lin_verified)
  void showVerify() {
    VerificationActivity.startWithUser(activity, user);
  }

  @OnClick(R.id.lin_prestasi)
  void showPrestasi() {
    startActivity(new Intent(activity, PrestasiActivity.class));
  }

  @OnClick(R.id.lin_review)
  void showReviews() {
    startActivity(new Intent(activity, ReviewsActivity.class));
  }

  @OnClick(R.id.lin_pengalaman)
  void showPengalaman() {
    startActivity(new Intent(activity, PengalamanActivity.class));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  public void initUsers(User userUpdate) {
    user = userUpdate;
  }
}
