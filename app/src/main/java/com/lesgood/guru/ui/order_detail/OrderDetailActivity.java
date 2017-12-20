package com.lesgood.guru.ui.order_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.Invoices;
import com.lesgood.guru.data.model.Order;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.ui.map.MapsActivity;
import com.lesgood.guru.util.DateFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import javax.inject.Inject;

/**
 * Created by Agus on 2/23/17.
 */

public class OrderDetailActivity extends BaseActivity {

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.view_progress)
  LinearLayout viewProgress;

  @Bind(R.id.txt_customer_name)
  TextView txtCustomerName;

  @Bind(R.id.txt_product)
  TextView txtProduct;

  @Bind(R.id.txt_tota_person)
  TextView txtSiswa;

  @Bind(R.id.txt_pertemuan)
  TextView txtPertemuan;

  @Bind(R.id.txt_date)
  TextView txtDate;

  @Bind(R.id.txt_order_id)
  TextView txtOrderId;

  @Bind(R.id.txt_status)
  TextView txtStatus;

  @Bind(R.id.txt_tarif)
  TextView txtTarif;

  @Bind(R.id.img_map)
  ImageView imgMap;

  @Bind(R.id.txt_detail_lokasi)
  TextView txtDetailLokasi;

  @Bind(R.id.txt_distance)
  TextView txtDistance;

  @Bind(R.id.txt_nama_siswa)
  TextView txtNamaSiswa;

  @Bind(R.id.txt_alamat_siswa)
  TextView txtAlamatSiswa;

  @Bind(R.id.txt_telp_siswa)
  TextView txtTelpSiswa;

  @Bind(R.id.txt_email_siswa)
  TextView txtEmailSiswa;

  @Bind(R.id.txt_nama_guru)
  TextView txtGuru;

  @Bind(R.id.txt_alamat_guru)
  TextView txtAlamatGuru;

  @Bind(R.id.txt_telp_guru)
  TextView txtTelpGuru;

  @Bind(R.id.txt_email_guru)
  TextView txtEmailGuru;

  @Bind(R.id.lin_action)
  LinearLayout linAction;

  @Inject
  Order order;

  @Inject
  User user;

  @Inject
  OrderDetailPresenter presenter;

  private LatLng latLng;

  public static void startWithOrder(BaseActivity activity, Order order) {
    Intent intent = new Intent(activity, OrderDetailActivity.class);
    BaseApplication.get(activity).createOrderDetailComponent(order);
    activity.startActivity(intent);

  }
  public static String KEY_PARAM_NOTIF = "Notif";
  public static void starFromNotif(BaseActivity activity, String order) {
    Intent intent = new Intent(activity, OrderDetailActivity.class);
    intent.putExtra(KEY_PARAM_NOTIF,order);
    activity.startActivity(intent);


  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order_detail);
    ButterKnife.bind(this);
    Bundle extra = getIntent().getExtras();
    if(extra != null){
      String param = extra.getString(KEY_PARAM_NOTIF);
      presenter.viewDetailOrder(param);

    }

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    init();
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.subscribe();
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenter.unsubscribe();
    BaseApplication.get(this).releaseOrderDetailComponent();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      finish();
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this)
        .getOrderDetailComponent()
        .plus(new OrderDetailActivityModule(this))
        .inject(this);
  }


  public void init() {
    txtOrderId.setText("#" + order.getOid());
    txtDetailLokasi.setText(order.getDetailLocation());
    String status = order.getStatus();
    if (status.equalsIgnoreCase("pending_murid")) {
      txtStatus.setText("Menunggu Pembayaran Murid");
    } else {
      txtStatus.setText("Menunggu Konfirmasi Pengajar");
    }

    txtDate.setText(DateFormatter.getDate(order.getPertemuanTime(), "EEE, dd MMM yyyy, HH:mm"));
    txtProduct.setText(order.getTitle());

    int fee = (int) (order.getFee() + 0.5d);
    int total = (int) (order.getTotal() + 0.5d);
    txtTarif.setText("Rp." + toRupiah(user.getStartFrom()));

    presenter.getGuru(order.getGid());
    presenter.getSiswa(order.getUid());
    presenter.getInvoice(order.getOid()+""+order.getCode());
    handleStatus(order.getStatus(),order.getStatusGantiGuru());

  }

  public void handleStatus(String status, String statusGantiGuru) {
    if (status.equalsIgnoreCase("pending_guru")) {
      linAction.setVisibility(View.VISIBLE);
    } else if (status.equalsIgnoreCase("change_guru")) {

    } else {
      linAction.setVisibility(View.VISIBLE);

    }

    if (status.equalsIgnoreCase("cancel_murid")) {
      String title = "Pesanan Dibatalkan";
      String desc = "Pesanan telah dibatalkan oleh murid";
      int icon = R.drawable.ic_appointment_24dp_primary;
      showAlertDialog(title, desc, icon);
    }
    if (statusGantiGuru.equalsIgnoreCase("request")){
      linAction.setVisibility(View.VISIBLE);
    }else {
      linAction.setVisibility(View.GONE);
    }
  }

  public void showProgress(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }

  private String toRupiah(int amount) {
    String angka = Integer.toString(amount);
    NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
    String rupiah = rupiahFormat.format(Double.parseDouble(angka));
    return rupiah;
  }


  @OnClick(R.id.btn_positif)
  void accept() {
    showProgress(true);
    Log.e("accept", "OrderDetailActivity" + order.getStatusGantiGuru());
    if (order.getStatusGantiGuru().equalsIgnoreCase("request")) {
      presenter.acceptChangeTeacher(order,"accept");
    } else {
      presenter.acceptOrder(order);
    }

  }

  @OnClick(R.id.btn_negatif)
  void decline() {
    showProgress(true);
    if (order.getStatusGantiGuru().equalsIgnoreCase("request")) {
      presenter.acceptChangeTeacher(order,"decline");
    } else {
      presenter.declineOrder(order);
    }
  }

  public void successAction(Order order) {
    showProgress(false);
    if (order.getStatus().equalsIgnoreCase("pending_murid")) {
      String title = "Terima Kasih";
      String desc = "Menunggu Pembayaran Murid";
      int icon = R.drawable.ic_appointment_24dp_primary;
      showAlertDialog(title, desc, icon);
    } else if (order.getStatus().equalsIgnoreCase("cancel_guru")) {
      String title = "Pembatalan pesanan";
      String desc = "Pesanan telah dibatalkan";
      int icon = R.drawable.ic_appointment_24dp_primary;
      showAlertDialog(title, desc, icon);
      presenter.updateOrder(order);
    } else if (order.getStatus().equalsIgnoreCase("change_guru")) {
      String title = "Penggatian guru";
      String desc = "Pesanan telah diterima";
      int icon = R.drawable.ic_appointment_24dp_primary;
      showAlertDialog(title, desc, icon);
    } else {
      showProgress(false);
      Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }
  }

  private void showAlertDialog(String title, String desc, int icon) {
    final Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    new Builder(this)
        .setTitle(title)
        .setMessage(desc)
        .setCancelable(false)
        .setPositiveButton("OK", (dialog, which) -> {
          // continue with delete
          dialog.dismiss();
          startActivity(intent);
        })
        .setIcon(icon)
        .show();
  }


  @OnClick(R.id.img_map)
  public void onMapCliked() {
    MapsActivity.start(this,latLng.latitude,latLng.longitude);
  }

  public void updateUI(Order order) {
    /*BaseApplication.get(this).createOrderDetailComponent(order);*/
    order = order;
    Log.e("OrderDetailActivity", "updateUI: " + order);
  }

  public void initDetailGuru(User user) {
    txtGuru.setText(user.getFull_name());
    txtAlamatGuru.setText(user.getFullAddress());
    txtTelpGuru.setText(user.getPhone());
    txtEmailGuru.setText(user.getEmail());
  }

  public void initDetailSiswa(User user) {
    txtCustomerName.setText(user.getFull_name());
    txtNamaSiswa.setText(user.getFull_name());
    txtEmailSiswa.setText(user.getEmail());
    txtTelpSiswa.setText(user.getPhone());
    txtAlamatSiswa.setText(user.getFullAddress());
    latLng = new LatLng(user.getLatitude(),user.getLongitude());
    String url =
        "http://maps.googleapis.com/maps/api/staticmap?zoom=16&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C"
            + user.getLatitude() + "," + user.getLongitude() + "+&sensor=false";

    Glide.with(this)
        .load(url)
        .placeholder(R.color.colorGrey400)
        .centerCrop()
        .dontAnimate()
        .into(imgMap);
  }

  public void initDetailInvoice(Invoices invoices) {
    txtSiswa.setText(String.valueOf(invoices.getTotalSiswa()));
    txtOrderId.setText("#" + invoices.getOid());
    txtPertemuan.setText(String.valueOf(invoices.getTotalPertemuan()) + " kali");
  }
}
