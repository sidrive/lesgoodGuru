package com.lesgood.guru.ui.wallet;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.model.Withdraw;
import com.lesgood.guru.ui.edit_profile.EditProfileActivity;
import com.lesgood.guru.util.TypefacedTextView;
import com.lesgood.guru.util.Utils;
import java.util.Calendar;
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

  @Inject
  WithdrawAdapter adapter;
  public static String KEY_UID = "uid";

  @Bind(R.id.tvSaldo)
  TypefacedTextView tvSaldo;
  @Bind(R.id.view_progress)
  LinearLayout viewProgress;
  @Bind(R.id.rcvRiwayat)
  RecyclerView rcvRiwayat;

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
    showWithdrawList();
  }

  private void showWithdrawList() {
    presenter.subscribe();
    rcvRiwayat.setAdapter(adapter);
    rcvRiwayat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
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

  public void updateUi(User userData) {
    BaseApplication.get(this).createUserComponent(userData);
    user = userData;
    tvSaldo.setText(Utils.getRupiah(userData.getSaldo()));
  }


  private OnClickListener listener = (dialog, which) -> {
    prosesWithdraw();
  };

  public void prosesWithdraw() {
    Calendar cal = Calendar.getInstance();
    cal.getTimeInMillis();
    Withdraw withdraw = new Withdraw();
    withdraw.setCreateAt(cal.getTimeInMillis());
    withdraw.setUid(user.getUid());
    withdraw.setSaldo(user.getSaldo());
    withdraw.setStatus("request");
    withdraw.setPaymentId(user.getUid());
    withdraw.setWid(user.getUid()+"_"+user.getSaldo());
    presenter.requestWitdraw(withdraw);
  }

  public void showDiloagDataPayment(String uid) {
    Utils.showDialog(this,
        "Data bank anda belum lengkap, lengkapi data bank sebelum melakuakan penarikan dana.",
        listenerDataPayment);

  }
  public void showDiloagRequentWithdraw(String uid) {
    Utils.showDialog(this,
        "Permintaan penarikan dana sebesar "+ user.getSaldo(),
        listener);

  }
  private OnClickListener listenerDataPayment = (dialog, which) -> {
    EditProfileActivity.startWithUser(this, user, false);
  };

  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }

  @OnClick({R.id.lin_tarikdana, R.id.lin_riwayat})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.lin_tarikdana:
        if (user.getSaldo() != 0) {
          presenter.chekPaymentPartner(user.getUid());
        } else {
          Utils.showDialog(this,
              "Anda tidak bisa melakukan permintaan penarikan dana dikarenakan saldo anda Rp. 0 ",
              null);
        }
        break;

    }
  }

  public void startAddWithdraw(Withdraw withdraw) {
    adapter.onItemAdded(withdraw);
    adapter.notifyDataSetChanged();
  }

  public void showDialogSuccessRequest() {
    Utils.showDialog(this,"Permintaan penarikan dana berhasil",null);
  }
}
