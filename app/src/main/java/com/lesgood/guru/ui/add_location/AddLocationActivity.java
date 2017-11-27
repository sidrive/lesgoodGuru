package com.lesgood.guru.ui.add_location;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.config.DefaultConfig;
import com.lesgood.guru.data.model.Location;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.util.Const;
import javax.inject.Inject;

/**
 * Created by Agus on 3/3/17.
 */

public class AddLocationActivity extends BaseActivity implements OnMapReadyCallback,
    OnCameraIdleListener, ConnectionCallbacks, OnConnectionFailedListener,LocationListener {

  @BindString(R.string.error_field_required)
  String strErrReuqired;

  @Bind(R.id.view_progress)
  LinearLayout progressBar;

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.rel_map)
  RelativeLayout relMap;


  @Inject
  User user;

  @Inject
  AddLocationPresenter presenter;
  private GoogleMap mMap;

  Location location;//this is user location model

  private double latitude = 0;
  private double longitude = 0;
  private SupportMapFragment mapFragment;
  private LocationRequest locationRequest;
  private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
  private long FASTEST_INTERVAL = 2000; /* 2 sec */
  private GoogleApiClient mGoogleApiClient;
  private android.location.Location mlocation;
  private IconGenerator iconGenerator;
  private Marker markerNewLocation;
  private LocationManager lm;
  DefaultConfig defaultConfig;
  public static void startWithUser(BaseActivity activity) {
    Intent intent = new Intent(activity, AddLocationActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_location);
    ButterKnife.bind(this);
    initActionbar();
    iconGenerator = new IconGenerator(this);
    iconGenerator.setStyle(IconGenerator.STYLE_BLUE);
    defaultConfig = new DefaultConfig(this);
    defaultConfig.setApiUrl(Const.BASE_URL_MAP);
    mGoogleApiClient = new Builder(getApplicationContext())
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
    mGoogleApiClient.connect();
    locationRequest = new LocationRequest();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(UPDATE_INTERVAL);
    locationRequest.setFastestInterval(FASTEST_INTERVAL);
    mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
    getCurrentLocationUser();

    location = new Location(user.getUid());
    location.setLat(0);
    location.setLng(0);
    Log.e("onCreate", "AddLocationActivity" + location.getLng());
    Log.e("onCreate", "AddLocationActivity" + location.getLat());
  }

  private void initActionbar() {
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  public void mapConnect() {
    mGoogleApiClient.connect();
  }

  public void mapDisconnect() {
    if (mGoogleApiClient.isConnected()) {
      LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
      mGoogleApiClient.disconnect();
    }
  }

  private void getCurrentLocationUser() {
    lm = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      if (isNetworkEnabled) {
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      } else if (isGPSEnabled) {
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      }
    }
  }


  public void init(Location location) {
    this.location = location;
    mapConnect();
    Log.e("init", "AddLocationActivity" + location.getAddress());
    if (location.getAddress().length() != 0) {
      latitude = location.getLat();
      longitude = location.getLng();
      LatLng latLng = new LatLng(latitude, longitude);
      Log.e("init", "AddLocationActivity" + latLng);
      handleNewLatLng(latLng);
      markUserLocation(latLng,"Here");
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_activity_brief, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      finish();
    }
    if (id == R.id.action_save) {
      saveMap();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    LatLng userCurrentLocation = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
    /*if (location.getLat() != 0 && location.getLng() != 0) {
      userCurrentLocation = new LatLng(location.getLat(), location.getLng());
      latitude = location.getLat();
      longitude = location.getLng();
      Log.e("onMapReady", "AddLocationActivity" + location.getLat());
      Log.e("onMapReady", "AddLocationActivity" + location.getLng());
      Log.e("onMapReady", "AddLocationActivity" + location.getAddress());

    }
*/
    mMap.setMapType(MAP_TYPE_NORMAL);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocation, 16));
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocation, 16));
    mMap.setOnCameraIdleListener(this);
    mMap.setMyLocationEnabled(true);
    mMap.setOnMapClickListener(onMapClickListener);
  }
  private void handleNewLatLng(LatLng pos) {
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,16));
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,16));
  }

  public void markUserLocation(LatLng maplocation, String title) {
    markerNewLocation = mMap.addMarker(new MarkerOptions()
        .position(maplocation)
        .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon(title))));
    markerNewLocation.showInfoWindow();

  }

  private OnMapClickListener onMapClickListener = latLng -> {
    if (markerNewLocation != null) {
      markerNewLocation.remove();
    }
    markUserLocation(latLng, "New Location");
    presenter.getAddressLocation(latLng);
  };

  private void saveMap() {
    LatLng latLng = mMap.getCameraPosition().target;
    latitude = latLng.latitude;
    longitude = latLng.longitude;
    finish();
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
  protected void onStart() {
    super.onStart();
    mapConnect();
    setLoadingProgress(true);
    presenter.subscribe();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mapConnect();
    setLoadingProgress(true);
    presenter.subscribe();
    getCurrentLocationUser();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mapDisconnect();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapDisconnect();
  }

  public void setLoadingProgress(boolean show) {
    if (show) {
      progressBar.setVisibility(View.VISIBLE);
    } else {
      progressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    mlocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    if (mlocation == null) {
      LocationServices.FusedLocationApi
          .requestLocationUpdates(mGoogleApiClient, locationRequest,
              this);
    } else {
      LatLng latLng = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
      handleNewLatLng(latLng);
    }
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }


  public void setAddressMap(String address){
    /*if (markerNewLocation != null) {
      markerNewLocation.remove();
    }
    markerNewLocation.setSnippet("here");
    markerNewLocation.showInfoWindow();*/
  }
  private android.location.LocationListener locationListener = new android.location.LocationListener() {
    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };

  @Override
  public void onLocationChanged(android.location.Location location) {

  }

  public void successAddLocation(Location location) {

  }
}
