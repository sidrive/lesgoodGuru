package com.lesgood.guru.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.util.TypefacedTextView;
import com.lesgood.guru.util.Utils;
import javax.inject.Inject;

/**
 * Created by Agus on 5/24/17.
 */

public class WalletActivity extends BaseActivity {

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Inject
  User user;

  @Inject
  WalletPresenter presenter;

  public static String KEY_UID = "uid";

  @Bind(R.id.tvSaldo)
  TypefacedTextView tvSaldo;

  private String uid;

  public static void start(Context context, String uid) {
    Intent starter = new Intent(context, WalletActivity.class);
    starter.putExtra(KEY_UID, uid);
    context.startActivity(starter);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_saldo);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    Bundle extra = getIntent().getExtras();
    if (extra != null) {
      uid = extra.getString(KEY_UID);

      presenter.getDetailUser(uid);
    }
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this)
        .getUserComponent()
        .plus(new WalletActivityModule(this))
        .inject(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }

    return super.onOptionsItemSelected(item);
  }

  public void updateUi(User user) {
    tvSaldo.setText(Utils.getRupiah(user.getSaldo()));
  }

  @OnClick(R.id.cvTarikDana)
  public void onCvTarikDanaClicked() {
  }

  @OnClick(R.id.cvRiwayat)
  public void onCvRiwayatClicked() {
  }
}
