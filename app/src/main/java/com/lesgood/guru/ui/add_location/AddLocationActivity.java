package com.lesgood.guru.ui.add_location;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.Province;
import com.lesgood.guru.data.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 3/3/17.
 */

public class AddLocationActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    @BindString(R.string.error_field_required)
    String strErrReuqired;

    @Bind(R.id.view_progress)
    LinearLayout progressBar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.txt_province)
    TextView inputProvince;

    @Bind(R.id.txt_kabupaten)
    TextView inputKabupaten;

    @Bind(R.id.rel_map)
    RelativeLayout relMap;

    @Bind(R.id.img_map)
    ImageView imgMap;

    @Inject
    User user;

    @Inject
    AddLocationPresenter presenter;

    private GoogleMap mMap;

    Location location;

    private boolean mapMode = false;

    private int provinceId = 0;
    private int kabupatenId = 0;

    private List<Province> provinces;
    private CharSequence[] charProvinces;
    private CharSequence[] charProvincesId;
    private List<LatLng> provinceLatLng;
    private int provinceVal;

    private List<Province> kabupatens;
    private CharSequence[] charKabupatens;
    private CharSequence[] charKabupatensId;
    private List<LatLng> kabupatenLatLng;
    private int kabupatenVal;

    private double latitude = 0;
    private double longitude = 0;

    public static void startWithUser(BaseActivity activity) {
        Intent intent = new Intent(activity, AddLocationActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        location = new Location(user.getUid());
        location.setLat(0);
        location.setLng(0);

    }


    public void init(Location location) {
        this.location = location;

        if (location.getProvince_name() != null) {
            inputProvince.setText(location.getProvince_name());
            provinceId = Integer.valueOf(location.getProvince_id());
            presenter.getChildren(location.getProvince_id());

            latitude = location.getLat();
            longitude = location.getLng();
            LatLng latLng = new LatLng(latitude, longitude);
            initMap(latLng);


        }
        if (location.getKabupaten_name() != null) {

            inputKabupaten.setText(location.getKabupaten_name());
            kabupatenId = Integer.valueOf(location.getKabupaten_id());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_brief, menu);
        return true;
    }


    public String getProvinceName(int id, List<Province> list){
        for (int i=0;i<list.size();i++){
            Province item = list.get(i);
            if (item.getId() == id){
                return item.getName();
            }
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (mapMode){
                hideMap();
            }else {
                finish();
            }
        }

        if (id == R.id.action_save) {
            if (mapMode){
                saveMap();
            }else{
                validate();
            }

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng indonesia = new LatLng(-0.789275, 113.921327);

        if (location.getLat() != 0 && location.getLng() != 0) {
            indonesia = new LatLng(location.getLat(), location.getLng());
            latitude = location.getLat();
            longitude = location.getLng();
        }

        initMap(indonesia);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 16));
        mMap.setOnCameraIdleListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                return false;
            }
        });
    }

    private void handleNewLatLng(LatLng pos){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
    }

    public void initMap(LatLng latLng){
        String url = "http://maps.googleapis.com/maps/api/staticmap?zoom=16&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C" + latLng.latitude + "," + latLng.longitude + "+&sensor=false";
        Log.d("initmap", "url = "+url);
        Glide.with(this)
                .load(url)
                .placeholder(R.color.colorShadow2)
                .centerCrop()
                .dontAnimate()
                .into(imgMap);

    }

    @OnClick(R.id.img_map)
    void showMap(){
        relMap.setVisibility(View.VISIBLE);
        mapMode = true;
    }

    private void hideMap(){
        relMap.setVisibility(View.GONE);
        mapMode = false;
    }

    private void saveMap(){
        LatLng latLng = mMap.getCameraPosition().target;
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        initMap(latLng);
        hideMap();
    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = mMap.getCameraPosition().target;
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .plus(new AddLocationActivityModule(this))
                .inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLoadingProgress(true);
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    public void setLoadingProgress(boolean show) {
        if (show){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }



    @OnClick(R.id.input_province)
    void showProvinces(){
        showDialogSelectProvince();
    }

    @OnClick(R.id.input_kabupaten)
    void showKabupatens(){
        showDialogSelectKabupaten();
    }


    private void validate() {
        inputProvince.setError(null);
        inputKabupaten.setError(null);

        boolean cancel = false;
        View focusView = null;


        if (provinceId == 0) {
            inputProvince.setError(strErrReuqired);
            focusView = inputProvince;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            setLoadingProgress(true);
            if (kabupatenId>0) {
                location.setKabupaten_id(String.valueOf(kabupatenId));
                location.setKabupaten_name(inputKabupaten.getText().toString());
            }


            location.setProvince_id(String.valueOf(provinceId));
            location.setProvince_name(inputProvince.getText().toString());
            location.setLat(latitude);
            location.setLng(longitude);

            presenter.createLocation(location);

        }

    }

    public void successAddLocation(Location location){
        String fullAddress = "";
        String address = "";
        String address2 = "";
        String kabupaten = "";
        String province = "";
        String zipCode = "";

        if (location.getAddress() != null) address = location.getAddress();
        if (location.getAddress_2() != null) address2 = " "+location.getAddress_2();
        if (location.getKabupaten_name() != null) kabupaten = ", "+location.getKabupaten_name();
        if (location.getProvince_name() != null) province = " "+location.getProvince_name();
        if (location.getZip_code() != null) zipCode = " "+location.getZip_code();

        fullAddress = address+address2+kabupaten+province+zipCode;

        user.setLatitude(location.getLat());
        user.setLongitude(location.getLng());
        user.setLocation(location.getProvince_name());
        user.setFullAddress(fullAddress);
        presenter.updateUserLocation(user);
        BaseApplication.get(this).createUserComponent(user);
        finish();
    }

    public void setProvinces(List<Province> list){
        setLoadingProgress(false);
        this.provinces = list;
        charProvinces = new CharSequence[list.size()];
        charProvincesId = new CharSequence[list.size()];
        provinceLatLng = new ArrayList<>();


        for (int i=0;i<list.size();i++){
            Province item = list.get(i);
            charProvinces[i] = item.getName();
            charProvincesId[i] = String.valueOf(item.getId());
            if (location.getProvince_name() != null){
                if (item.getName().equals(location.getProvince_name())){
                    provinceVal = i;
                }
            }
            provinceLatLng.add(new LatLng(item.getLatitude(), item.getLongitude()));
        }

    }

    public void setKabupaten(List<Province> list){
        this.kabupatens = list;
        charKabupatens = new CharSequence[list.size()];
        charKabupatensId = new CharSequence[list.size()];
        kabupatenLatLng = new ArrayList<>();

        for (int i=0;i<list.size();i++){
            Province item = list.get(i);
            charKabupatens[i] = item.getName();
            charKabupatensId[i] = String.valueOf(item.getId());
            if (location.getKabupaten_name() != null){
                if (item.getName().equals(location.getKabupaten_name())){
                    kabupatenVal = i;
                }
            }
//            kabupatenLatLng.add(new LatLng(item.getLatitude(), item.getLongitude()));
        }
    }

    private void showDialogSelectProvince() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Select Province");
        alert.setSingleChoiceItems(charProvinces, provinceVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String whichIs = charProvinces[which].toString();
                String whiISId = charProvincesId[which].toString();

                inputProvince.setText(whichIs);
                provinceVal = which;
                provinceId = Integer.valueOf(whiISId);

                LatLng latLng = provinceLatLng.get(which);
                handleNewLatLng(latLng);
                initMap(latLng);

                latitude = latLng.latitude;
                longitude = latLng.longitude;

                handleSelectProvince(whiISId);

                dialog.dismiss();

            }
        });
        alert.show();
    }

    public void handleSelectProvince(String id){
        presenter.getChildren(id);
    }

    private void showDialogSelectKabupaten() {
        final AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
        alert2.setTitle("Select Kabupaten");
        alert2.setSingleChoiceItems(charKabupatens, kabupatenVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String whichIs = charKabupatens[which].toString();
                String whiISId = charKabupatensId[which].toString();

                inputKabupaten.setText(whichIs);
                kabupatenVal = which;
                kabupatenId = Integer.valueOf(whiISId);

//                LatLng latLng = kabupatenLatLng.get(which);
//                handleNewLatLng(latLng);


                dialog.dismiss();

            }
        });
        alert2.show();
    }

}
